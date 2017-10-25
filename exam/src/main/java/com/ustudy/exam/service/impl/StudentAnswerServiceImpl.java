package com.ustudy.exam.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.StudentPaperDao;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.model.StudentPaper;
import com.ustudy.exam.service.StudentAnswerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {
	
	private static final Logger logger = LogManager.getLogger(StudentAnswerServiceImpl.class);
	
	@Resource
	private StudentPaperDao paperDaoImpl;
	
	@Resource
	private StudentObjectAnswerDao answerDaoImpl;

	public boolean saveStudentsAnswers(int examId, int egsId, JSONObject data) {
		
		logger.debug("saveStudentsAnswers -> examId:" + examId + ", egsId:" + egsId + ", data:" + data);
		
		if (null != data.get("sipisModelList")) {
			JSONArray studentPapers = data.getJSONArray("sipisModelList");
			for (int i = 0; i < studentPapers.size(); i++) {
				JSONObject object = studentPapers.getJSONObject(i);
				StudentPaper paper = new StudentPaper();
				paper.setExamId(examId);
				paper.setEgsId(egsId);
				if(null != object.get("ESID")){
					paper.setStuExamNo(object.getInt("ESID"));
				}
				if(null != object.get("BatchNum")){
					paper.setBatchNum(object.getInt("BatchNum"));
				}
				if(null != object.get("StuAPPath")){
					paper.setPaperImg(object.getString("StuAPPath"));
				}
				if(null != object.get("PaperStatus")){
					paper.setPaperStatus(object.getString("PaperStatus"));
				}
				if(null != object.get("HistoryErrorStatusList")){
					String errorStatus = object.getJSONArray("HistoryErrorStatusList").toString();
					errorStatus = errorStatus.replace("[", "").replace("]", "");
					paper.setErrorStatus(errorStatus);
				}
				paperDaoImpl.insertStudentPaper(paper);
				
				JSONArray studentScores = object.getJSONArray("Exam_Student_Score");
				for (int j = 0; j < studentScores.size(); j++) {
					JSONObject object_ = studentScores.getJSONObject(j);
					
					StudentObjectAnswer answer = new StudentObjectAnswer();
					answer.setPaperid(paper.getId());
					if(null != object_.get("questNum")){
						answer.setQuesno(object_.getInt("questNum"));
					}
					if(null != object_.get("answerHas")){
						answer.setAnswerHas(object_.getInt("answerHas"));
					}
					if(null != object_.get("stuObjectAnswer")){
						answer.setAnswer(object_.getString("stuObjectAnswer"));
					}
					
					answerDaoImpl.insertStudentObjectAnswer(answer);
				}
			}
			
			return true;
		}else {
			return false;
		}
	}
	
}
