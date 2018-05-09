package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

public class QuesAnswerDiv implements Serializable {

	private static final long serialVersionUID = 6622342777771917094L;

	private Long id;
	private String quesno;
	private String type;
	private String branch;
	private float score;
	private Long quesid;
	private Long egsId;
	private int step;
	private String remark;

    private List<QuesAnswerDiv> steps;

	public Long getEgsId() {
		return egsId;
	}

	public void setEgsId(Long egsId) {
		this.egsId = egsId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getQuesno() {
        return quesno;
    }

    public void setQuesno(String quesno) {
        this.quesno = quesno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}
	
    public int getStep() {
        return step;
    }
    
    public void setStep(int step) {
        this.step = step;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<QuesAnswerDiv> getSteps() {
        return steps;
    }

    public void setSteps(List<QuesAnswerDiv> steps) {
        this.steps = steps;
    }

    @Override
	public String toString() {
		return "QuesAnswerDiv [id=" + id + 
		        ", quesno=" + quesno + 
		        ", type=" + type + 
		        ", branch=" + branch + 
		        ", score=" + score + 
		        ", quesid=" + quesid + 
                ", egsId=" + egsId +  
                ", step=" + step +  
                ", remark=" + remark + 
		        "]";
	}

}
