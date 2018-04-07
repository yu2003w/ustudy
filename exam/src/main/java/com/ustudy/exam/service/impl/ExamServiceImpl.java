package com.ustudy.exam.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesareaDao;
import com.ustudy.exam.mapper.ExamMapper;
import com.ustudy.exam.mapper.MarkProgMapper;
import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrBrife;
import com.ustudy.exam.model.GrClsBrife;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.Quesarea;
import com.ustudy.exam.model.statics.EgsMarkMetrics;
import com.ustudy.exam.service.ExamService;
import com.ustudy.exam.utility.ExamUtil;
import com.ustudy.info.util.InfoUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamServiceImpl implements ExamService {

	private static final Logger logger = LogManager.getLogger(ExamServiceImpl.class);

	@Resource
	private ExamDao examDaoImpl;

	@Autowired
	private QuesAnswerDao questionDaoImpl;

	@Resource
	private QuesareaDao quesareaDao;

	@Autowired
	private ExamMapper exM;
	
	@Autowired
	private MarkProgMapper mpM;
	
	@Autowired
	private MarkTaskMapper mtM;

	public List<Exam> getAllExams() {
		return examDaoImpl.getAllExams();
	}

	public List<Exam> getExamsByStatus(String status) {
		logger.debug("getExamsByStatus -> status:" + status);
		String sid = ExamUtil.retrieveSessAttr("orgId");
		if (sid == null || sid.isEmpty()) {
			logger.error("getExamsByStatus(), no school id found, maybe user not login");
			throw new RuntimeException("getExamsByStatus(), no school id found, maybe user not login");
		}
		return examDaoImpl.getExamsByStatus(status, sid);
	}

	public Exam getExamsById(Long id) {
		logger.debug("getExamsById -> id:" + id);
		return examDaoImpl.getExamsById(id);
	}

	public ArrayList<Map<String, Object>> getSubjects() {
		return examDaoImpl.getSubjects();
	}

	public ArrayList<Map<String, Object>> getGrades() {
		return examDaoImpl.getGrades();
	}

	public JSONArray getExams(Boolean finished, Long gradeId, Long subjectId, String startDate, String endDate,
			String name) {

		JSONArray result = new JSONArray();

		String orgId = ExamUtil.retrieveSessAttr("orgId");
		if (orgId == null || orgId.isEmpty()) {
			logger.error("getExams(), no school id found, maybe user not login");
			throw new RuntimeException("getExams(), no school id found, maybe user not login");
		}

		try {
			List<Exam> exams = examDaoImpl.getExams(finished, orgId, gradeId, subjectId, startDate, endDate, name);

			for (Exam exam : exams) {

				List<Map<String, Object>> grades = examDaoImpl.getExamGrades(exam.getId());
				List<Map<String, Object>> subjects = examDaoImpl.getExamSubjects(exam.getId());
				long count = examDaoImpl.getExamStudengCount(exam.getId());

				JSONObject object = JSONObject.fromObject(exam);
				object.put("grades", grades);
				object.put("subjects", subjects);
				object.put("studentCount", count);

				result.add(object);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return result;

	}

	public JSONArray getExamSummary(Long examId) {

		logger.info("getExamSummary -> examId:" + examId);

		List<Map<String, Object>> examSubjects = examDaoImpl.getExamSummary(examId);

		if (null != examSubjects && examSubjects.size() > 0) {

			Map<Long, Long> gradeStudentCounts = getGradeStudentCounts(examId);

			Map<Long, Long> egsStudentCounts = getEgsStudentCounts(examId);

			/* comment out by jared
			Map<Long, Long> subjectPaperCounts = getSubjectPaperCounts(examId);
			Map<Long, Long> subjectAnswers = getSubjectAnswers(examId);
			*/
			Map<Long, Map<String, Long>> subjectQuestions = getSubjectQuestions(examId);

			// return summary(examSubjects, gradeStudentCounts, subjectPaperCounts, subjectAnswers, subjectQuestions);
			return summary(examSubjects, gradeStudentCounts, egsStudentCounts, subjectQuestions);
		}

		return null;
	}

	private Map<Long, Long> getGradeStudentCounts(Long examId) {

		Map<Long, Long> counts = new HashMap<>();

		List<Map<String, Object>> gradeStudentCounts = examDaoImpl.getGradeStudentCounts(examId);
		for (Map<String, Object> map : gradeStudentCounts) {
			long gradeId = (int) map.get("gradeId");
			long count = (long) map.get("count");
			counts.put(gradeId, count);
		}

		return counts;
	}

	private Map<Long, Long> getEgsStudentCounts(Long examId) {

		Map<Long, Long> counts = new HashMap<>();

		long branchCount = examDaoImpl.getBranchCount(examId);

		// has branch art or sci
		if (branchCount > 0) {
			long artMathCount = examDaoImpl.getArtMathCount(examId);

			List<Map<String, Object>> egsStudentCounts = examDaoImpl.getEgsStudentCounts(examId, artMathCount);
			for (Map<String, Object> map : egsStudentCounts) {
				long egsId = (int) map.get("egsId");
				long count = (long) map.get("count");
				counts.put(egsId, count);
			}
		}
		return counts;
	}

	/*private Map<Long, Long> getSubjectPaperCounts(Long examId) {

		Map<Long, Long> counts = new HashMap<>();

		List<Map<String, Object>> subjectPaperCounts = examDaoImpl.getSubjectPaperCounts(examId);
		for (Map<String, Object> map : subjectPaperCounts) {
			long egsId = (int) map.get("egsId");
			long count = (long) map.get("count");
			counts.put(egsId, count);
		}

		return counts;
	}*/

	/*private Map<Long, Long> getSubjectAnswers(Long examId) {

		Map<Long, Long> counts = new HashMap<>();

		List<Map<String, Object>> subjectAnswers = examDaoImpl.getSubjectAnswers(examId);
		for (Map<String, Object> map : subjectAnswers) {
			long egsId = (int) map.get("egsId");
			long count = (long) map.get("count");
			counts.put(egsId, count);
		}

		return counts;
	}*/

	private Map<Long, Map<String, Long>> getSubjectQuestions(Long examId) {

		Map<Long, Map<String, Long>> counts = new HashMap<>();

		List<Map<String, Object>> subjectQuestions = examDaoImpl.getSubjectQuestions(examId);
		for (Map<String, Object> map : subjectQuestions) {
			long egsId = (int) map.get("egsId");
			String type = map.get("type").toString();
			int startno = (int) map.get("startno");
			int endno = (int) map.get("endno");
			float score = (float)map.get("score");
			
			if (type.equals("单选题") || type.equals("多选题") || type.equals("判断题")) {
				Map<String, Long> m = counts.get(egsId);
				long objectCount = 0;
				long objectScore = 0;
				if (null == m) {
					m = new HashMap<>();
				} else {
					if (null != m.get("objectCount")) {
						objectCount = m.get("objectCount");
					}
					if (null != m.get("objectScore")) {
						objectScore = m.get("objectScore");
					}
				}
				int count = endno - startno + 1;
				objectCount += count;
				objectScore += count * score;

				m.put("objectCount", objectCount);
				m.put("objectScore", objectScore);

				counts.put(egsId, m);
			} else {
				Map<String, Long> m = counts.get(egsId);
				long subjectCount = 0;
				long answerCount = 0;
				long subjectScore = 0;
				if (null == m) {
					m = new HashMap<>();
				} else {
					if (null != m.get("subjectCount")) {
						subjectCount = m.get("subjectCount");
						answerCount = m.get("answerCount");
					}
					if (null != m.get("subjectScore")) {
						subjectScore = m.get("subjectScore");
					}
				}

				int count = endno - startno + 1;
				subjectCount += count;
				subjectScore += count * score;

				String markMode = "单评";
				if (null != m.get("markMode")) {
					markMode = map.get("markMode").toString();
				}
				if (markMode.equals("双评")) {
					answerCount += count * 2;
				} else {
					answerCount += count;
				}

				m.put("subjectCount", subjectCount);
				m.put("answerCount", answerCount);
				m.put("subjectScore", subjectScore);

				counts.put(egsId, m);
			}
		}

		return counts;
	}

	private JSONArray summary(List<Map<String, Object>> examSubjects, Map<Long, Long> gradeStudentCounts, Map<Long, Long> egsStudentCounts, 
			Map<Long, Map<String, Long>> subjectQuestions) {

		JSONArray result = new JSONArray();
		// exam meta data
		JSONArray array = new JSONArray();

		Map<Long, List<JSONObject>> subjects = new HashMap<>();

		for (Map<String, Object> map : examSubjects) {
			Long gradeId = Long.parseLong(String.valueOf(map.get("gradeId")));
			String gradeName = String.valueOf(map.get("gradeName"));
			String examDate = String.valueOf(map.get("examDate"));
			Long egsId = Long.parseLong(String.valueOf(map.get("egsId"))); 
			List<JSONObject> list = subjects.get(gradeId);
			long gradeStudentCount = 0;
			if (null == list) {
				list = new ArrayList<>();
				JSONObject object = new JSONObject();
				object.put("gradeId", gradeId);
				object.put("gradeName", gradeName);
				object.put("examDate", examDate);

				long count = 0;
				if (null != gradeStudentCounts.get(gradeId)) {
					count = gradeStudentCounts.get(gradeId);
				}
				object.put("studentCount", count);
				gradeStudentCount = count;

				array.add(object);
			} else {
				for (int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);
					if (gradeId == object.getLong("gradeId")) {
						gradeStudentCount = object.getLong("studentCount");
						break;
					}
				}
			}

			JSONObject subject = new JSONObject();
			subject.put("subjectName", map.get("subjectName"));
			subject.put("subjectId", map.get("subjectId"));
			subject.put("status", map.get("status"));
			subject.put("template", map.get("template"));
			subject.put("markSwitch", map.get("markSwitch"));

			long count = 0;
			if (null != egsStudentCounts.get(egsId)) {
				count = egsStudentCounts.get(egsId);
			} else {
				count = gradeStudentCount;
			}
			subject.put("studentCount", count);

			String answerPaper = "";
			if (null == map.get("answerPaper") || map.get("answerPaper").toString().equals("null")) {
				answerPaper = "";
			} else {
				answerPaper = map.get("answerPaper").toString();
			}
			subject.put("answerPaper", answerPaper);

			String questionsPaper = "";
			if (null == map.get("questionsPaper") || map.get("questionsPaper").toString().equals("null")) {
				questionsPaper = "";
			} else {
				questionsPaper = map.get("questionsPaper").toString();
			}
			subject.put("questionsPaper", questionsPaper);

			subject.put("egsId", egsId);
			
			// added by Jared, check table MarkTask by to determine whether all marktask are assigned
			subject.put("taskDispatch", this.isMarkTaskDispatched(egsId));
			subject.put("answerSet", this.isAnswerSet(egsId));
			logger.trace("summary(), taskDispatch->" + subject.get("taskDispatch") + 
					", answerSet->" + subject.get("answerSet"));
			
			/* commented by Jared
			long paperCount = 0;
			if (null != subjectPaperCounts.get(egsId)) {
				paperCount = subjectPaperCounts.get(egsId);
			} */
			
			EgsMarkMetrics emm = mpM.getOverallMarkMetrics(egsId);
			if (emm == null) {
				logger.error("summary(), failed to retrieve mark metrics for egs->" + egsId);
				throw new RuntimeException("failed to retrieve mark metrics for egs->" + egsId);
			}
			subject.put("paperCount", emm.getNum());

			long objectCount = 0;
			long objectScore = 0;
			long subjectCount = 0;
			long subjectScore = 0;
			long answerCount = 0;

			Map<String, Long> questions = subjectQuestions.get(egsId);
			if (null != questions) {
				objectCount = questions.get("objectCount") == null ? 0 : questions.get("objectCount");
				objectScore = questions.get("objectScore") == null ? 0 : questions.get("objectScore");
				subjectCount = questions.get("subjectCount") == null ? 0 : questions.get("subjectCount");
				subjectScore = questions.get("subjectScore") == null ? 0 : questions.get("subjectScore");
				answerCount = questions.get("answerCount") == null ? 0 : questions.get("answerCount");
			}

			subject.put("objectCount", objectCount);
			subject.put("objectScore", objectScore);
			subject.put("subjectCount", subjectCount);
			subject.put("subjectScore", subjectScore);

			answerCount = emm.getTotal();
			long markedCount = emm.getMarked();
			/* commented by Jared
			if (null != subjectAnswers.get(egsId)) {
				markedCount = subjectAnswers.get(egsId);
			}*/
			if (answerCount == 0 || markedCount == 0) {
				subject.put("progressRate", 0);
			} else {
				float progressRate = (float) markedCount / answerCount * 100;
				subject.put("progressRate", new DecimalFormat("###.##").format(progressRate));
			}

			list.add(subject);

			subjects.put(gradeId, list);
		}

		if (array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = array.getJSONObject(i);
				long gradeId = object.getLong("gradeId");

				JSONObject robject = new JSONObject();

				robject.put("gradeId", gradeId);
				robject.put("gradeName", object.getString("gradeName"));
				robject.put("examDate", object.getString("examDate"));
				robject.put("studentCount", object.getLong("studentCount"));
				robject.put("subjects", subjects.get(gradeId));

				result.add(robject);
			}
		}

		return result;
	}

	public JSONArray getTeacherExams() {

		JSONArray result = new JSONArray();

		String orgId = InfoUtil.retrieveSessAttr("orgId");
		orgId = "001";
		if (orgId == null || orgId.isEmpty()) {
			logger.error("getExams(), no school id found, maybe user not login");
			throw new RuntimeException("getExams(), no school id found, maybe user not login");
		}

		List<Map<String, Object>> exams = examDaoImpl.getTeacherExams(orgId);
		if (null != exams && exams.size() > 0) {
			List<Long> egsIds = new ArrayList<>();
			for (Map<String, Object> exam : exams) {
				if (null != exam.get("egsId")) {
					long egsId = (int) exam.get("egsId");
					egsIds.add(egsId);
				}
			}

			Map<Long, List<Map<String, Object>>> questions = getQuestions(egsIds);

			for (Map<String, Object> exam : exams) {
				JSONObject object = JSONObject.fromObject(exam);

				if (null != exam.get("egsId")) {
					long egsId = (int) exam.get("egsId");
					object.put("questions", questions.get(egsId));
				}

				result.add(object);
			}
		}

		return result;
	}

	private Map<Long, List<Map<String, Object>>> getQuestions(List<Long> egsIds) {

		Map<Long, List<Map<String, Object>>> result = new HashMap<>();

		List<QuesAnswer> questions = questionDaoImpl.getQuestions(egsIds);
		if (null != questions && questions.size() > 0) {
			for (QuesAnswer question : questions) {
				if (null != question.getExamGradeSubId()) {
					long egsId = question.getExamGradeSubId();
					List<Map<String, Object>> subQues = result.get(egsId);
					if (null == subQues) {
						subQues = new ArrayList<>();
					}
					long id = question.getId();
					int startno = question.getStartno();
					int endno = question.getEndno();

					Map<String, Object> map = new HashMap<>();
					map.put("id", id);
					if (startno != endno) {
						map.put("name", startno + "-" + endno);
					} else {
						map.put("name", String.valueOf(startno));
					}

					subQues.add(map);
					result.put(egsId, subQues);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * getSubjectQuestionPapers[根据考试科目、题块号，返回该题的所有答题卡] 创建人: dulei 创建时间: 2018年1月3日
	 * 下午10:17:37
	 *
	 * @Title: getSubjectQuestionPapers
	 * @param subId
	 *            考试科目
	 * @param quesId
	 *            题块号
	 * @return 该题的所有答题卡
	 */
	public JSONArray getSubjectQuestionPapers(long egsId, long quesId) {

		JSONArray result = new JSONArray();

		List<Integer> pagenos = getPageNos(quesId);
		if (pagenos.size() > 0) {
			List<Map<String, Object>> papers = examDaoImpl.getSubjectQuestionPapers(egsId, quesId);
			if (null != papers && papers.size() > 0) {
				Map<String, List<Map<String, Object>>> markImgs = getSubjectQuestionMarkImgs(egsId, quesId);
				for (Map<String, Object> map : papers) {
					if (null != map.get("paperImg") && null != map.get("examCode")) {
						String examCode = map.get("examCode").toString();
						List<Map<String, Object>> markImg = markImgs.get(examCode);
						if (null == markImg) {
							markImg = new ArrayList<>();
						}
						map.put("markImgs", markImg);

						String[] paperImgs = map.get("paperImg").toString().split(",");
						String quesImgs = "";
						for (int pageno : pagenos) {
							if (pageno >= 0 && pageno < paperImgs.length) {
								if (quesImgs.length() > 0) {
									quesImgs += "," + paperImgs[pageno];
								} else {
									quesImgs += paperImgs[pageno];
								}
							}
						}
						map.put("paperImg", quesImgs);
						JSONObject object = JSONObject.fromObject(map);
						result.add(object);
					}

				}
			}
		}

		return result;
	}

	private Map<String, List<Map<String, Object>>> getSubjectQuestionMarkImgs(long egsId, long quesId) {
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		List<Map<String, Object>> markImgs = examDaoImpl.getSubjectQuestionMarkImgs(egsId, quesId);
		if (null != markImgs && markImgs.size() > 0) {
			for (Map<String, Object> map : markImgs) {
				if (null != map.get("examCode")) {
					String examCode = map.get("examCode").toString();
					List<Map<String, Object>> list = result.get(examCode);
					if (null == list) {
						list = new ArrayList<>();
					}
					map.remove("examCode");
					list.add(map);
					result.put(examCode, list);
				}
			}
		}
		return result;
	}

	private List<Integer> getPageNos(long quesId) {
		List<Integer> pagenos = new ArrayList<>();
		List<Quesarea> quesareas = quesareaDao.getQuesareas(quesId);
		if (null != quesareas && quesareas.size() > 0) {
			for (Quesarea quesarea : quesareas) {
				pagenos.add(quesarea.getPageno());
			}
		}

		return pagenos;
	}

	@Override
	public List<ExamGrBrife> getExamGrInfo(String schid) {
		List<ExamGrBrife> egL = exM.getExamGrBrife(schid);

		logger.debug("getExamGrInfo(), " + egL.toString());

		// populate class info here
		for (ExamGrBrife eg : egL) {
			List<GrClsBrife> grL = eg.getGrades();
			for (GrClsBrife grC : grL) {
				grC.setCls(exM.getClassByGrId(grC.getGrId()));
			}
		}
		logger.debug("getExamGrInfo(), " + egL.toString());
		return egL;
	}

	private boolean isMarkTaskDispatched(Long id) {
		List<MarkTask> mtL = mtM.getMarkTasksByEgs(id);
		for (MarkTask mt: mtL) {
			if (!mt.isValid()) {
				logger.warn("isMarkTaskDispatched(), mark task assignment is not completed for " + mt.getQuestionId());
				return false;
			}
		}
		return true;
	}
	
	private boolean isAnswerSet(Long id) {
		
		// retrieve answer settings for egs firstly
		List<QuesAnswer> quesAns = questionDaoImpl.getQuesAnswerForValidation(id);
		for (QuesAnswer qa: quesAns) {
			if (!qa.isValid()) {
				logger.warn("isAnswerSet(), answer setting for question is not completed->" + qa.toString());
				return false;
			}
		}
		return true;
	}
	
}
