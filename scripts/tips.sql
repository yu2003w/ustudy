常用的sql指令用于采集分数相关信息
1, 文理科缺靠学生信息统计
select name, exam_code, clsname, pid from
(SELECT ee.id, ee.exam_code, ee.class_id, class.cls_name as clsname, ee.name, paper.id as pid 
FROM ustudy.examinee as ee left join class on class.id = ee.class_id 
left join paper on (paper.exam_code = ee.exam_code and paper.exam_grade_sub_id = 64)
where ee.gradeid = 48 and class.cls_type = 'art' and paper.id is null) tb1
order by exam_code
2, 不分科缺靠学生信息统计
select name, exam_code, class_name, pid from
(SELECT ee.id, ee.exam_code, ee.class_id, ee.class_name, ee.name, paper.id as pid 
FROM ustudy.examinee as ee left join class on class.id = ee.class_id 
left join paper on (paper.exam_code = ee.exam_code and paper.exam_grade_sub_id = 59) 
where ee.gradeid = 48) tb1
where pid is null order by exam_code
3, 各科目总分统计
SELECT sum(if((question.type in ('单选题', '多选题', '判断题')), question.score * (question.endno - question.startno + 1), question.score)) as score, 
egs.id, subject.name, grade.grade_name FROM ustudy.question 
left join examgradesub as egs on egs.id = question.exam_grade_sub_id 
left join subject on subject.id = egs.sub_id 
left join grade on grade.id = egs.grade_id
left join exam on exam.id = egs.examid
where exam.id =11 group by egs.id
4, 各科成绩统计，不分科
select ee.exam_code, ee.name, class.cls_name, score, rank from examinee as ee 
left join class on class.id = ee.class_id 
left join subscore on subscore.stuid = ee.id 
left join examgradesub as egs on (egs.examid = ee.examid and egs.grade_id = ee.gradeid and egs.id = subscore.exam_grade_sub_id) 
where egs.id = 28 order by rank
5, 学校考试信息
select exam.id, exam_name, exam_date, exam.type, sch.schid, sch.schname from exam 
left join examschool as esh on exam.id = esh.examid 
left join school as sch on sch.schid = esh.schid
6,统计分科成绩
九年级文综
select exam_code, name, (a116+a3335) as '历史', (a1732+a3638) as '地理' from 
(select tb1.id, tb1.exam_code, tb1.name, tb1.a116, tb2.a1732, tb3.a3335, tb4.a3638 from 
(SELECT paper.id, paper.exam_code, ee.name, sum(oans.score) as a116 FROM ustudy.paper 
left join obj_answer as oans on oans.paperid = paper.id 
left join examinee as ee on ee.exam_code = paper.exam_code
where exam_grade_sub_id = 113 and quesno >= 1 and quesno <= 16 group by paper.id, paper.exam_code, ee.name) tb1
left join (SELECT paper.id, paper.exam_code, ee.name, sum(oans.score) as a1732 FROM ustudy.paper 
left join obj_answer as oans on oans.paperid = paper.id 
left join examinee as ee on ee.exam_code = paper.exam_code
where exam_grade_sub_id = 113 and quesno >= 17 and quesno <= 32 group by paper.id, paper.exam_code, ee.name) tb2 
on (tb1.exam_code = tb2.exam_code and tb1.name = tb2.name and tb1.id = tb2.id) 
left join (SELECT paper.id, ee.exam_code, ee.name, sum(score) as a3335 FROM ustudy.answer 
left join paper on paper.id = paperid 
left join examinee as ee on ee.exam_code = paper.exam_code 
where quesid in (407,403,406) group by paperid, ee.exam_code, ee.name order by ee.exam_code) tb3 
on (tb3.exam_code = tb1.exam_code and tb3.name = tb1.name and tb1.id = tb3.id) 
left join (SELECT paper.id, ee.exam_code, ee.name, sum(score) as a3638 FROM ustudy.answer 
left join paper on paper.id = paperid 
left join examinee as ee on ee.exam_code = paper.exam_code 
where quesid in (408,404,409) group by paperid, ee.exam_code, ee.name order by ee.exam_code) tb4 
on (tb4.exam_code = tb1.exam_code and tb4.name = tb1.name and tb1.id = tb4.id) 
order by tb1.exam_code) tbl5

从subscore获取分科成绩
SELECT ee.exam_code as '考号', ee.name as '姓名', subscore.score as '总分', subscore.rank as '排名', 
 scs.score1 as '政治', scs.rank1, scs.score2 as '历史', scs.rank2 FROM ustudy.subscore 
left join (SELECT scs1.parent_id, scs1.sub_id as sub1, scs1.score as score1, scs1.rank as rank1, 
scs2.sub_id as sub2, scs2.score as score2, scs2.rank as rank2
FROM ustudy.subchildscore as scs1 
inner join ustudy.subchildscore as scs2 on (scs1.parent_id = scs2.parent_id and scs1.id > scs2.id) ) 
as scs on scs.parent_id = subscore.id 
left join examinee as ee on ee.id = subscore.stuid 
 where subscore.exam_grade_sub_id = 18 order by ee.exam_code

7, 获取各科成绩明细
主观题
(SELECT ee.exam_code, ee.name, question.quesno, answer.score FROM ustudy.answer 
left join paper on paper.id = paperid 
left join question on answer.quesid = question.id 
left join examinee as ee on ee.exam_code = paper.exam_code 
where question.exam_grade_sub_id = 108 and question.type not in ('单选题','多选题', '判断题') and question.quesno > 0 
order by ee.exam_code,  question.startno ) 
union (SELECT ee.exam_code, ee.name, anst.quesno, anst.score FROM ustudy.answer 
left join paper on paper.id = paperid 
left join question on answer.quesid = question.id 
left join answer_step as anst on anst.answer_id = answer.id
left join examinee as ee on ee.exam_code = paper.exam_code 
where question.exam_grade_sub_id = 108 and question.type not in ('单选题','多选题', '判断题') and question.quesno = 0 
order by ee.exam_code, question.startno, anst.quesno) order by exam_code, quesno
客观题
SELECT paper.exam_code, ee.name, oans.quesno, oans.answer FROM ustudy.paper 
left join obj_answer as oans on oans.paperid = paper.id 
left join examinee as ee on ee.exam_code = paper.exam_code 
left join question on (question.exam_grade_sub_id = paper.exam_grade_sub_id and oans.quesno >= question.startno and question.endno >= oans.quesno) 
where paper.exam_grade_sub_id = 98 and question.type in ('单选题','多选题', '判断题') 
order by paper.exam_code, oans.quesno

8, 获取每个考生已上传试卷
SELECT ee.exam_code, ee.name, count(*) as num, 
(select count(*) from examgradesub where examid =11 and grade_id = 48 and temp_upload =1) as expected  FROM ustudy.paper 
left join examinee as ee on ee.exam_code = paper.exam_code 
 where ee.examid = 11 and gradeid= 48 group by ee.exam_code

9, 搜索客观题数量不符的试卷并标记成异常卷
找到异常卷id，以（id， id2）方式输出
select group_concat(pid) from 
(select exam_code, num, id as pid from  
(SELECT ee.exam_code, count(*) as num, paper.id FROM ustudy.obj_answer 
left join paper on obj_answer.paperid = paper.id 
left join examinee as ee on ee.exam_code = paper.exam_code 
left join examgradesub as egs on egs.id = paper.exam_grade_sub_id 
where egs.id = 31 group by ee.exam_code, paper.id) tb1 
where num != 13) tb2
update ustudy.paper set error_status = '2', paper_status = '2' where id in (ids)


