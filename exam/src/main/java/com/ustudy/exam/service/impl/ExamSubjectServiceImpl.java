package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.SubscoreDao;
import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.mapper.ConfigMapper;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.Subject;
import com.ustudy.exam.model.score.ChildObjScore;
import com.ustudy.exam.model.score.ChildSubScore;
import com.ustudy.exam.model.score.SubChildScore;
import com.ustudy.exam.model.score.PaperSubScore;
import com.ustudy.exam.model.MarkImage;
import com.ustudy.exam.model.score.SubScore;
import com.ustudy.exam.model.score.ObjAnswer;
import com.ustudy.exam.model.score.DblAnswer;
import com.ustudy.exam.model.score.UncommonAnswer;
import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.exam.service.impl.cache.PaperCache;
import com.ustudy.exam.service.impl.cache.ScoreCache;
import com.ustudy.exam.utility.OSSMetaInfo;
import com.ustudy.exam.utility.OSSUtil;
import com.ustudy.info.mapper.SchoolMapper;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

	private static final Logger logger = LogManager.getLogger(ExamSubjectServiceImpl.class);

	@Autowired
	private ExamSubjectDao egsDaoImpl;
	
	@Autowired
	private SubscoreDao scoreDaoImpl;

	@Resource
	private ExamDao examDao;
	
	@Autowired
	private PaperCache paperC;
	
	@Autowired
    private ScoreCache scoC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	@Autowired
	private QuesAnswerDao qaDao;

	@Autowired
	private SchoolMapper schM;
	
	@Autowired
	private ConfigMapper cgM;

	public List<ExamSubject> getExamSubjects(Long subjectId, Long gradeId, String start, String end, String examName) {
		logger.trace("getExamSubjects(), retrieving all exam subjects.");

		List<ExamSubject> esL = egsDaoImpl.getAllExamSubject(subjectId, gradeId, start, end, examName);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId) {
		logger.debug("getExamSubjects(), examId:" + examId);
		List<ExamSubject> esL = egsDaoImpl.getAllExamSubjectByExamId(examId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId) {
		logger.debug("getExamSubjects(), examId:" + examId + ",gradeId:" + gradeId);
		
		List<ExamSubject> esL = egsDaoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId, Long subjectId) {
		logger.debug("getExamSubjects(), examId:" + examId + ",gradeId:" + gradeId + ",subjectId:" + subjectId);
		
		List<ExamSubject> esL = egsDaoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public ExamSubject getExamSubject(Long id) {
		logger.debug("getExamSubject -> id:" + id);
		ExamSubject es = egsDaoImpl.getExamSubjectById(id);
		// populate sub/obj answer set status
		this.checkAnswerSet(es);
		es.setTaskDispatch(this.isMarkTaskDispatched(es.getId()));
		return es;
	}

	public boolean saveBlankAnswerPaper(Long id, String fileName) {
		logger.debug("saveBlankAnswerPaper -> id:" + id + ",fileName:" + fileName);
		try {
			egsDaoImpl.saveBlankAnswerPaper(id, fileName);
			return true;
		} catch (Exception e) {
			logger.error("saveBlankAnswerPaper(), failed with exception->" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	public boolean saveBlankQuestionsPaper(Long id, String fileName) {
		logger.debug("saveBlankQuestionsPaper -> id:" + id + ",fileName:" + fileName);
		try {
			egsDaoImpl.saveBlankQuestionsPaper(id, fileName);
			return true;
		} catch (Exception e) {
			logger.error("saveBlankQuestionsPaper(), failed with exception->" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean getMarkSwitchById(Long id) {
		logger.debug("getMarkSwitch -> id:" + id);
		ExamSubject es = egsDaoImpl.getMarkSwitchById(id);
		return es.getMarkSwitch();
	}

	public List<ExamSubject> getLastExamSubjects() {

		Exam exam = examDao.getLastExam();
		if (null != exam && exam.getId() > 0) {
			return getExamSubjects(exam.getId());
		} else {
			return null;
		}

	}

	public boolean updateMarkSwitch(Long egsId, Boolean release) {
		try {
			egsDaoImpl.updateMarkSwitchById(egsId, release);

			// TODO: 清除缓存
			//paperC.clearSubCache(String.valueOf(egsId));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateMarkSwitch(Long examId, Long gradeId, Long subjectId, Boolean release) {
		try {
			egsDaoImpl.updateMarkSwitch(examId, gradeId, subjectId, release);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	public boolean updateExamSubPapers(Long egsId) {
		try {
			// 1. merge the paper images of double marking
			  /* 1.1 list the double marking answers
				{paperid: xx, quesid: xx, qarea_id: xx, paper_img: xx, 
				mark_img: xx, x: xx, y: xx}
			  */
			List<MarkImage> dmis = egsDaoImpl.getDblMarkImgs(egsId);
        	  /* 1.2 upload the images to oss and insert to dmark_img table
        	  */
        	if (uploadMarkImgs(dmis) == false) {
        		return false;
        	}

        	// 2. get the list of all marking images
        	List<MarkImage> finalMarkImgs = egsDaoImpl.getFinalMarkImgs(egsId);

        	// 3. merge the whole paper image, upload to oss and insert to subscore table
        	if (mergePaperImg(finalMarkImgs) == false) {
        		return false;
        	}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	private boolean uploadMarkImgs(List<MarkImage> mis) {
		
		for(int i=0; i<mis.size(); i+=2) {
			//1. upload the mark images to oss
			MarkImage mi1 = mis.get(i);
			MarkImage mi2 = mis.get(i+1);

			if (!mi1.getPaperId().equals(mi2.getPaperId()) ||
				!mi1.getPageNo().equals(mi2.getPageNo()) ||
				!mi1.getPosX().equals(mi2.getPosX()) ||
				!mi1.getPosY().equals(mi2.getPosY())) {
 				logger.error("uploadMarkImgs(), unmatched double marking records-> #1: " + mi1.toString() + " #2: " + mi2.toString());
				throw new RuntimeException("uploadMarkImgs(), unmatched double marking records found!");
			}

			String paperImg = mi1.getPaperImg();
			String markImg1 = "A" + mi1.getMarkImg(); // mark image is not suitable for watermark, add answer image. 
			String markImg2 = mi1.getMarkImg();
			
			if (paperImg == null || paperImg.isEmpty() || markImg1 == null || markImg1.isEmpty() || markImg2 == null || markImg2.isEmpty()) {
				logger.error("uploadMarkImgs(), empty image found in double marking-> #1: " + mi1.toString() + " #2: " + mi2.toString());
				throw new RuntimeException("uploadMarkImgs(), emtpy image found in double marking!");
			}

			// mark image naming rule: M_{questionNo}_{regionNo}_{teacherId}_{paperName}.png
			// remove the teacherId from the targetName
			String[] strArray = markImg1.split("_");
			String targetName = "";

			for (int j=0; j<strArray.length; j++) {
				if (j == 3) continue;
				if (j != strArray.length-1) {
					targetName += strArray[j] + "_";
				} else {
					targetName += strArray[j];
				}
			}

			List<MarkImage> markImgs = new ArrayList<>();
			markImgs.add(mi2);

			try {
				if (OSSUtil.getClient() == null) {
					// need to initialize OSSMetaInfo
					logger.info("uploadMarkImgs(), initialize OSSClient before use");
					synchronized(OSSMetaInfo.class) {
						if (OSSUtil.getClient() == null) {
							OSSMetaInfo omi = cgM.getOSSInfo("oss");
							logger.debug("uploadMarkImgs(), OSS Client init with->" + omi.toString());
							OSSUtil.initOSS(omi);
						}
					}
				}
				OSSUtil.putObject(markImg1, targetName, markImgs, true);
			} catch (Exception e) {
				logger.error("uploadMarkImgs(), failed to upload image to oss -> " + e.getMessage());
				return false;
			}

			// 2. insert into dmark_img table
			egsDaoImpl.updateDblMarkImgs(mi1.getQuesId(), mi1.getPaperId(), mi1.getQareaId(), targetName);

			// MarkImage dblMarkImg = new MarkImage(mi1.getPaperId(), mi1.getQuesId(), mi1.getQareaId(), mi1.getPageNo(), mi1.getPaperImg(), 
			// 	targetName, mi1.getPosX(), mi1.getPosY());

			// dblMarkImgs.add(dblMarkImg);
		}
		return true;
		// return dblMarkImgs;
	}


	private boolean mergePaperImg(List<MarkImage> mis) {		
		String prePaperImg = "";
		Long prePaperId = 0L;
		String preMarkImg = "";

		String curPaperImg = "";
		Long curPaperId = 0L;

		int regionCount = 0;		
		List<MarkImage> markImgs = new ArrayList<>();

		try {
			if (OSSUtil.getClient() == null) {
				// need to initialize OSSMetaInfo
				logger.info("mergePaperImg(), initialize OSSClient before use");
				synchronized(OSSMetaInfo.class) {
					if (OSSUtil.getClient() == null) {
						OSSMetaInfo omi = cgM.getOSSInfo("oss");
						logger.debug("mergePaperImg(), OSS Client init with->" + omi.toString());
						OSSUtil.initOSS(omi);
					}
				}
			}
			for(MarkImage mi : mis) {
				curPaperImg = mi.getPaperImg();
				curPaperId = mi.getPaperId();
				if (!curPaperImg.equals(prePaperImg)) {
					if(!prePaperImg.isEmpty()) {
						String targetName = "AM_" + prePaperImg;
						// first region
						if (regionCount == 1) {
							OSSUtil.putObject(prePaperImg, targetName, markImgs, false);
						// later region
						} else {
							OSSUtil.putObject(targetName, targetName, markImgs, false);						
						}
						markImgs.clear();
						markImgs.add(mi);
						regionCount = 1;
						if (prePaperId.equals(curPaperId)) {
							preMarkImg += targetName + ";";
						} else {
							addFinalMarks(prePaperId, preMarkImg + targetName);
							egsDaoImpl.updateMarkImg(prePaperId, preMarkImg + targetName);
							preMarkImg = "";
						}
						prePaperImg = curPaperImg;
						prePaperId = curPaperId;
					} else {
						markImgs.add(mi);
						prePaperImg = curPaperImg;
						prePaperId = curPaperId;
						regionCount = 1;
					}
				} else {
					String targetName = "AM_" + prePaperImg;
					// first region
					if (regionCount == 1) {
						OSSUtil.putObject(prePaperImg, targetName, markImgs, false);
					// later region
					} else {
						OSSUtil.putObject(targetName, targetName, markImgs, false);						
					}
					markImgs.clear();
					markImgs.add(mi);
					prePaperImg = curPaperImg;
					prePaperId = curPaperId;
					regionCount++;
				}
			}
			String targetName = "AM_" + prePaperImg;
			// first region
			if (regionCount == 1) {
				OSSUtil.putObject(prePaperImg, targetName, markImgs, false);
			// later region
			} else {
				OSSUtil.putObject(targetName, targetName, markImgs, false);						
			}
			if (!preMarkImg.isEmpty()) {
				if (preMarkImg.contains(targetName)) {
					addFinalMarks(prePaperId, preMarkImg.substring(0, preMarkImg.length()-1));
					egsDaoImpl.updateMarkImg(prePaperId, preMarkImg.substring(0, preMarkImg.length()-1));
				} else {
					addFinalMarks(prePaperId, preMarkImg + targetName);
					egsDaoImpl.updateMarkImg(prePaperId, preMarkImg + targetName);				
				}
			} else {
				addFinalMarks(prePaperId, targetName);
				egsDaoImpl.updateMarkImg(prePaperId, targetName);
			}
		} catch (Exception e) {
			logger.error("mergePaperImg(), failed to merge images -> " + e.getMessage());
			return false;
		}
		return true;
	}

	private void addFinalMarks(Long paperId, String paperImgs) {
		//1. get scores
		PaperSubScore paperScore = scoreDaoImpl.getPaperSubScores(paperId);

		if (paperImgs.length() <= 0 || paperScore == null) {
			return;
		}

		String[] imgs = paperImgs.split(";");

		try{
			if (OSSUtil.getClient() == null) {
				// need to initialize OSSMetaInfo
				logger.info("addFinalMarks(), initialize OSSClient before use");
				synchronized(OSSMetaInfo.class) {
					if (OSSUtil.getClient() == null) {
						OSSMetaInfo omi = cgM.getOSSInfo("oss");
						logger.debug("addFinalMarks(), OSS Client init with->" + omi.toString());
						OSSUtil.initOSS(omi);
					}
				}
			}
			OSSUtil.putObject(imgs[0], imgs[0], "" + paperScore.getScore(), 50, 50);
		} catch (Exception e) {
			logger.error("addFinalMarks(), failed to add marks -> " + e.getMessage());
			return;
		}

		//2. get objective answers
		
		List<ObjAnswer> answers = egsDaoImpl.getObjAnsScore(paperId);

		if (answers != null && answers.size() > 0) {
			int pageno = answers.get(0).getPageno();
			int x = answers.get(0).getX() + answers.get(0).getW();
			int titleX = answers.get(0).getX() + answers.get(0).getW()/2;
			int y = answers.get(0).getY();
			String preType = "";
			int start = 0;
			int end = 0;
			List<String> marks = new ArrayList<String>();
			String answerText = "";

			for (ObjAnswer answer : answers) {
				if (!answer.getType().equals(preType)) {
					preType = answer.getType();
					marks.add(answer.getType());
					start = answer.getQuesno();
					end = answer.getQuesno();
					answerText = (answer.getScore() == 0 ? "*" : answer.getAnswer());
				} else {
					if ((answer.getQuesno() == end + 1) && (answer.getQuesno() > start + 4) || (answer.getQuesno() != end +1)) {
						marks.add(start + "-" + end + ": " + answerText);
						start = answer.getQuesno();
						end = answer.getQuesno();
						answerText = (answer.getScore() == 0 ? "*" : answer.getAnswer());
					} else {
						end = answer.getQuesno();
						answerText = answerText + (answer.getType().equals("多选题") ? "," : "" ) + (answer.getScore() == 0 ? "*" : answer.getAnswer());
					}
				}
			}

			if(answerText.length() >= 0) {
				marks.add(start + "-" + end + ": " + answerText);
			}

			try{
				if (OSSUtil.getClient() == null) {
					// need to initialize OSSMetaInfo
					logger.info("addFinalMarks(), initialize OSSClient before use");
					synchronized(OSSMetaInfo.class) {
						if (OSSUtil.getClient() == null) {
							OSSMetaInfo omi = cgM.getOSSInfo("oss");
							logger.debug("addFinalMarks(), OSS Client init with->" + omi.toString());
							OSSUtil.initOSS(omi);
						}
					}
				}
				//OSSUtil.putObject(imgs[pageno], imgs[pageno], marks, x, y);
				OSSUtil.putObject(imgs[pageno], imgs[pageno], "" + paperScore.getObjScore(), titleX, y);
			} catch (Exception e) {
				logger.error("addFinalMarks(), failed to add marks -> " + e.getMessage());
				return;
			}
		}

		//3. get double markings.

		List<DblAnswer> dblAnswers = egsDaoImpl.getDblAns(paperId);
		List<String> dblMarks = new ArrayList<String>();
		if (dblAnswers != null && dblAnswers.size()>0) {
			int preQuesId = 0;
			String preTeacName = "";
			int dblX = 0;
			int dblY = 0;
			int dblPageno = 0;
			logger.debug("dblAnswers size->" + dblAnswers.size() + " paperid is " + paperId);
			logger.debug("dblMarks size->" + dblMarks.size());
			for (DblAnswer dblAnswer: dblAnswers) {
				logger.debug("dblAnswer ->" + dblAnswer.toString());
				if (dblAnswer.getQuesId() != preQuesId) {
					logger.debug("dblAnswer different quesid");
					preQuesId = dblAnswer.getQuesId();
					preTeacName = dblAnswer.getTeacName();
					if (dblMarks.size() > 0) {
						try{
							if (OSSUtil.getClient() == null) {
								// need to initialize OSSMetaInfo
								logger.info("addFinalMarks(), initialize OSSClient before use");
								synchronized(OSSMetaInfo.class) {
									if (OSSUtil.getClient() == null) {
										OSSMetaInfo omi = cgM.getOSSInfo("oss");
										logger.debug("addFinalMarks(), OSS Client init with->" + omi.toString());
										OSSUtil.initOSS(omi);
									}
								}
							}
							OSSUtil.putObject(imgs[dblPageno], imgs[dblPageno], dblMarks, dblX, dblY);
							logger.debug("dblAnswer put object");
							dblMarks.clear();
						} catch (Exception e) {
							logger.error("addFinalMarks(), failed to add marks -> " + e.getMessage());
							return;
						}
					}
					dblPageno = dblAnswer.getPageno();
					dblX = dblAnswer.getX() + dblAnswer.getW();
					dblY = dblAnswer.getY();
					dblMarks.add("双评阅卷");
					dblMarks.add("分差设置: " + dblAnswer.getScoreDiff() + "分");
					if(dblAnswer.getIsFinal() == false) {
						dblMarks.add("阅卷人A: " + dblAnswer.getTeacName() + " (" + dblAnswer.getScore() + ")");
					} else {
						dblMarks.add("终评人: " + dblAnswer.getTeacName() + " (" + dblAnswer.getScore() + ")");
					}
				} else {
					logger.debug("dblAnswer same quesid");
					if (!dblAnswer.getTeacName().equals(preTeacName)) {
						preTeacName = dblAnswer.getTeacName();
						if(dblAnswer.getIsFinal() == false) {
							dblMarks.add("阅卷人B: " + dblAnswer.getTeacName() + " (" + dblAnswer.getScore() + ")");
						} else {
							dblMarks.add("终评人: " + dblAnswer.getTeacName() + " (" + dblAnswer.getScore() + ")");
						}
					}
				}
			}
			if (dblMarks.size() > 0) {
				try{
					if (OSSUtil.getClient() == null) {
						// need to initialize OSSMetaInfo
						logger.info("addFinalMarks(), initialize OSSClient before use");
						synchronized(OSSMetaInfo.class) {
							if (OSSUtil.getClient() == null) {
								OSSMetaInfo omi = cgM.getOSSInfo("oss");
								logger.debug("addFinalMarks(), OSS Client init with->" + omi.toString());
								OSSUtil.initOSS(omi);
							}
						}
					}
					OSSUtil.putObject(imgs[dblPageno], imgs[dblPageno], dblMarks, dblX, dblY);
					logger.debug("dblAnswer put object");
					dblMarks.clear();
				} catch (Exception e) {
					logger.error("addFinalMarks(), failed to add marks -> " + e.getMessage());
					return;
				}
			}
		}

		//4. set BEST for top 2% students and FAQ for bottom 1% students(not including blank answer), only set for Chinese articles.
		/* paperid -> is yuwen and is last question?
		           -> is not BEST or FAQ
				   	-> if rank <= (number of students)*2%, set markType to BEST, get the position, put object
					-> if rank >= (number of students)*99%, set markType to FAQ, get the position, put object
		*/
		addFlag(paperId, imgs);
	}

	private void addFlag(Long paperId, String[] imgs) {
		logger.debug("addFlag(), paperId=" + paperId);
		String flag = "NONE";
		List<UncommonAnswer> answers = egsDaoImpl.getTopAns(paperId);
		if (answers != null && answers.size()>0) {
			flag = "BEST";
		} else {
			answers = egsDaoImpl.getBottomAns(paperId);
			if (answers != null && answers.size()>0) {
				flag = "FAQ";
			}
		}
		// set flag in the DB
		if (flag.equals("NONE")) {
			return;
		} else {
			UncommonAnswer answer = answers.get(0);
			egsDaoImpl.updateMarkFlag(answer.getQuesId(), paperId, flag);
			try{
				if (OSSUtil.getClient() == null) {
					// need to initialize OSSMetaInfo
					logger.info("addFlag(), initialize OSSClient before use");
					synchronized(OSSMetaInfo.class) {
						if (OSSUtil.getClient() == null) {
							OSSMetaInfo omi = cgM.getOSSInfo("oss");
							logger.debug("addFlag(), OSS Client init with->" + omi.toString());
							OSSUtil.initOSS(omi);
						}
					}
				}
				if(flag.equals("BEST")) {
					OSSUtil.addMarkImage(answer.getAnsMarkImg(), "icon-bestanswer.png", 0, 0);
					OSSUtil.addMarkImage(imgs[answer.getPageno()], "icon-bestanswer.png", answer.getX(), answer.getY());
				} else if (flag.equals("FAQ")) {
					OSSUtil.addMarkImage(answer.getAnsMarkImg(), "icon-faq.png", 0, 0);
					OSSUtil.addMarkImage(imgs[answer.getPageno()], "icon-faq.png", answer.getX(), answer.getY());
				}
			} catch (Exception e) {
				logger.error("addFlag(), failed to add flag -> " + e.getMessage());
				return;
			}
		}
	}

	@Override
	@Transactional
	public boolean updateEgsScoreStatus(Long egsId, Boolean release) {
		
		logger.debug("updateEgsScoreStatus(), egsid=" + egsId + ", release=" + release);
		try {
			egsDaoImpl.updateExamSubjectStatusById(egsId, release);
			if (release) {
				// calculate subscore for egs，scores of object questions should be recalculated, 
				// caller should ensure that obj score calculation is completed
				SummaryEgsScore(egsId);
				// clear cache
				paperC.clearSubCache(String.valueOf(egsId));
				// 更新考试状态
				// TODO: possibly need to optimize logic and sql instructions here
				examDao.updateExamStatusByEgsid(egsId,"2");
			}else {
			    // 更新考试状态
			    examDao.updateExamStatusByEgsid(egsId,"1");
			    scoC.setScoreColStatus(egsId.intValue(), false);
            }
			return true;
		} catch (Exception e) {
			logger.error("updateEgsScoreStatus(), failed with exception->" + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateExamSubjectStatus(Long examId, Long gradeId, Long subjectId, Boolean release) {
		try {
			egsDaoImpl.updateExamSubjectStatus(examId, gradeId, subjectId, release);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	@Transactional
	private void SummaryEgsScore(Long egsId) {
		logger.debug("SummaryEgsScore(), to retrieve object question scores for " + egsId);
		List<SubScore> scores = retrieveObjScores(egsId);
		logger.debug("SummaryEgsScore(), to retrieve subject question scores for " + egsId);
		Map<Long, SubScore> subjScores = retrieveSubScores(egsId);
		
		// calculate total score for each exmainee in this egs
		if (scores.size() > 0) {
			for (SubScore ss : scores) {
				SubScore subjS = subjScores.get(ss.getStuId());
				if(null != subjS){
					// combine object and subject score here
				    ss.setSubScore(subjS.getSubScore());
				    List<SubChildScore> scsOL = ss.getSubCSL();
				    List<SubChildScore> scsSL = subjS.getSubCSL();
				    for (SubChildScore scss : scsSL) {
				    	boolean found = false;
				    	for (SubChildScore scs: scsOL) {
					    	if (scs.getSubName().compareTo(scss.getSubName()) == 0) {
					    		scs.setScore(scss.getScore() + scs.getScore());
					    		found = true;
					    		break;
					    	}
					    }
				    	if (!found) {
				    		// branch only existed in subject question scores
				    		scsOL.add(scss);
				    	}
			    	}
				    
				}
				else {
					logger.warn("SummaryEgsScore(), failed to find subject scores for eid=" + ss.getStuId() + 
							", egs=" + egsId);
				}
				ss.setScore(ss.getObjScore() + ss.getSubScore());
			}
		} else {
			logger.info("SummaryEgsScore(), no object scores for " + egsId + ", only subject scores be calculated");
			scores = new ArrayList<SubScore>(subjScores.values());
			for (SubScore ss: scores) {
				ss.setScore(ss.getSubScore());
			}
		}
		
		logger.debug("SummaryEgsScore(), score combined, to calculate rank now.");
		logger.trace("SummaryEgsScore(), before ranking->" + scores.toString());
		if(scores.size() > 0){
			Collections.sort(scores);
			// updated by jared, need to accumulate rank when score equals
			int rank =1;
			for (int i = 0; i < scores.size(); i++) {
				if (i == 0) {
					scores.get(i).setRank(rank);
				}
				else {
					if (scores.get(i).getScore().compareTo(scores.get(i-1).getScore()) == 0) {
						scores.get(i).setRank(scores.get(i-1).getRank());
					}
					else {
						scores.get(i).setRank(rank);
					}
				}
				rank++;
			}
			// save subscores, insert on duplicate key udpate
			// Noted: mybatis batch insert failed to write back ids at this time, need to retrieve id of
			// subscores for child subscore save
			logger.debug("SummaryEgsScore(), subscore before save:" + scores.toString());
			int ret = scoreDaoImpl.saveSubscores(scores);
			logger.debug("SummaryEgsScore(), number of items saved for sub score is " + ret);
			// populate sub child scores
			List<SubChildScore> childScores = new ArrayList<SubChildScore>();
			
			Subject mainSub = schM.getSubjectByEgsId(egsId);
			logger.trace("SummaryEgsScore(), main subject->" + mainSub.toString());
			if (mainSub != null && mainSub.getChildSubs() != null && mainSub.getChildSubs().size() > 0) {
				// current subject contains child subjects, need to retrieve ids of SubScores
				List<Long> ssIDs = scoreDaoImpl.getSSIDsByEgsId(egsId);
				if (ssIDs == null || ssIDs.size() != scores.size()) {
					logger.error("SummaryEgsScore(), number of ids not matched with that of scores for egs " + egsId);
					throw new RuntimeException("number of subscore ids not matched with that of scores");
				}
				for (int i = 0; i < scores.size(); i++) {
					scores.get(i).setId(ssIDs.get(i));
				}
			}
		
			// load subject into hashmap
			List<Subject> subs = schM.getAllSubjects();
			Map<String, Long> subMap = new HashMap<String, Long>();
			for (Subject sub: subs) {
				subMap.put(sub.getName(), sub.getId());
			}
			
			for (SubScore ss: scores) {
				logger.trace("SummaryEgsScore(), subscore saved->" + ss.getId() + "," + ss.toString());
				List<SubChildScore> scsL = ss.getSubCSL();
				scsL.forEach(item->{
					item.setParentId(ss.getId());
					item.setSubId(subMap.get(item.getSubName()));
					childScores.add(item);
				});
			}
			// save sub child scores
			if (!childScores.isEmpty()) {
				// need to sort child subject scores, then calculate rank
				childScores.sort(new Comparator<SubChildScore>() {
					@Override
					public int compare(SubChildScore a, SubChildScore b) {
						if (a.getSubName().compareTo(b.getSubName()) == 0) {
							if (a.getScore() == b.getScore())
								return 0;
							else if (a.getScore() > b.getScore())
								return -1;
							else if (a.getScore() < b.getScore())
								return 1;
						} 
						return a.getSubName().compareTo(b.getSubName());
					}
					
				});
				
				rank = 1;
				for (int i = 0; i < childScores.size(); i++) {
					if (i == 0) {
						childScores.get(i).setRank(rank);
					}
					else {
						// child subject changed, need to reset rank
						if (childScores.get(i - 1).getSubName().compareTo(childScores.get(i).getSubName()) != 0) {
							rank = 1;
							childScores.get(i).setRank(rank);
						} else {
							if (childScores.get(i).getScore() == childScores.get(i - 1).getScore()) {
								childScores.get(i).setRank(childScores.get(i - 1).getRank());
							} else
								childScores.get(i).setRank(rank);
						}
					}
					rank++;
				}
				logger.debug("SummaryEgsScore(), sub child score before saved->" + childScores.toString());
				ret = scoreDaoImpl.saveSubChildScores(childScores);
				logger.debug("SummaryEgsScore(), number of saved sub child scores is " + ret);
			}
				
		}
	}

	/**
	 * @param egsId
	 * @return List of SubScore only contains object question scores
	 */
	private List<SubScore> retrieveObjScores(Long egsId) {
		Map<Long, SubScore> scoreM = new HashMap<Long, SubScore>();
		/*
		 * returned data with format as below,
		 * score, eid, branch
		 * data is grouped by eid and branch
		 */
		List<ChildObjScore> scoL = egsDaoImpl.retrieveEgsObjScores(egsId);
		if (scoL != null && !scoL.isEmpty()) {
			logger.debug("retrieveObjScores(), retrieved " + scoL.size() + " items for " + egsId);
			for (ChildObjScore sco: scoL) {
				SubScore ss = scoreM.get(sco.getEid());
				if (ss == null) {
					ss = new SubScore(sco.getEid(), egsId);
					scoreM.put(sco.getEid(), ss);
				}
				
				// accumulate object score here
				ss.setObjScore(ss.getObjScore() + sco.getScore());
				if (sco.getBranch().compareTo("不分科") != 0)
					ss.getSubCSL().add(new SubChildScore(sco.getBranch(), sco.getScore()));
			}
		}
		logger.trace("retrieveObjScores(), obj scores retrieved for " + egsId + "->" + scoreM.values());
		return new ArrayList<SubScore>(scoreM.values());
	}

	/**
	 * @param egsId
	 * @return subject scores for specified egs
	 */
	private Map<Long, SubScore> retrieveSubScores(Long egsId) {
		Map<Long, SubScore> scoreM = new HashMap<Long, SubScore>();
		
		List<ChildSubScore> cssL = egsDaoImpl.retrieveEgsSubScores(egsId);
		logger.trace("retrieveSubScores(), retrieved " + cssL.size() + " items of subject scores for " + egsId);
		if (cssL != null && !cssL.isEmpty()) {
			for (ChildSubScore css: cssL) {
				SubScore ss = scoreM.get(css.getEid());
				if (ss == null) {
					ss = new SubScore(css.getEid(), egsId);
					scoreM.put(css.getEid(), ss);
				}
				// accumulate subject question score for examinee
				ss.setSubScore(ss.getSubScore() + css.getRealScore());
				if (css.getBranch().compareTo("不分科") != 0) {
					List<SubChildScore> subCS = ss.getSubCSL();
					boolean found = false;
					for (SubChildScore subc : subCS) {
						if (subc.getSubName().compareTo(css.getBranch()) == 0) {
							subc.setScore(subc.getScore() + css.getRealScore());
							found = true;
							break;
						}
					}
					if (found == false) {
						SubChildScore subc = new SubChildScore(css.getBranch(), css.getRealScore());
						subCS.add(subc);
					}
				}
			}
		}
		
		logger.debug("retrieveSubScores(), detailed subject scores for " + egsId + "->" + scoreM.values());
		return scoreM;
	}

	/**
	 * Check whether all subject questions' answer are set at runtime rather than determine that via
	 * fields in table of database. As settings are allowed to be changed before examination completed
	 * @param es --- ExamSubject to be populated
	 */
	private void checkAnswerSet(ExamSubject es) {
		// retrieve answer settings for egs firstly
		List<QuesAnswer> quesAns = qaDao.getQuesAnswerForValidation(es.getId());
		es.setSubAnsSet(true);
		es.setObjAnsSet(true);
		if (quesAns == null || quesAns.isEmpty()) {
			logger.warn("checkAnswerSet(), no answer set records retrieved, maybe templates not uploaded "
					+ "for egs " + es.getId());
			es.setSubAnsSet(false);
			es.setObjAnsSet(false);
		}
		
		for (QuesAnswer qa: quesAns) {
			if (qa.getType() == null || qa.getType().isEmpty()) {
				es.setSubAnsSet(false);
				es.setObjAnsSet(false);
				return;
			}
			if ((qa.getType().equals("单选题") || qa.getType().equals("多选题") || qa.getType().equals("判断题")) 
					&& !qa.isValid() ) {
				logger.warn("checkAnswerSet(), answer setting is not completed for question->" + qa.toString());
				es.setObjAnsSet(false);
			}
			else if (!qa.isValid()) {
				es.setSubAnsSet(false);
			}
		}
		logger.trace("checkAnswerSet(), subAnsSet=" + es.isSubAnsSet() + ", objAnsSet=" + es.isObjAnsSet() + 
				",egsid=" + es.getId());
	}

	/**
	 * Check whether all questions' mark task for specified egs are already dispatched
	 * @param id --- egsid
	 * @return
	 */
	private boolean isMarkTaskDispatched(Long id) {
		
		List<MarkTask> mtL = mtM.getMarkTasksByEgs(id);
		if (mtL == null || mtL.isEmpty()) {
			logger.warn("isMarkTaskDispatched(), no mark task records retrieved, maybe templates not "
					+ "uploaded for egs " + id);
			return false;
		}
		for (MarkTask mt: mtL) {
			if (!mt.isValid()) {
				logger.warn("isMarkTaskDispatched(), mark task assignment is not completed for "
						+ "quesid=" + mt.getQuestionId() + ", task->" + mt.toString());
				return false;
			}
		}
		return true;
	}

	private void populateExamSubStatus(List<ExamSubject> esL) {
		logger.trace("populateExamSubStatus(), populate answer set and task dispatch status for examsubjects");
		for (ExamSubject es: esL) {
			checkAnswerSet(es);
			es.setTaskDispatch(this.isMarkTaskDispatched(es.getId()));
		}
	}
}
