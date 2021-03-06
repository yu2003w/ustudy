package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubScore implements Serializable, Comparable<SubScore> {

    private static final long serialVersionUID = -1704696113960776084L;

    private long id = 0;
    // examinee id not examinee code
    private long stuId = 0;
    private long egsId = 0;
    private String subName = null;
	private float score = 0;
    private float subScore = 0;
    private float objScore = 0;
    private int rank = 0;
    
    @JsonProperty("childScores")
    private List<SubChildScore> subCSL = null;

    public SubScore() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public SubScore(long stuId, long egsId) {
		super();
		this.stuId = stuId;
		this.egsId = egsId;
		subCSL = new ArrayList<SubChildScore>();
	}

	public SubScore(String subName, Float score, int rank) {
		super();
		this.subName = subName;
		this.score = score;
		this.rank = rank;
	}
	
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStuId(long stuId) {
		this.stuId = stuId;
	}

	public void setEgsId(long egsId) {
		this.egsId = egsId;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public void setSubScore(float subScore) {
		this.subScore = subScore;
	}

	public void setObjScore(float objScore) {
		this.objScore = objScore;
	}

	public long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuid) {
        this.stuId = stuid;
    }

    public long getEgsId() {
        return egsId;
    }

    public void setEgsId(Long egsId) {
        this.egsId = egsId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public float getSubScore() {
        return subScore;
    }

    public void setSubScore(Float subScore) {
        this.subScore = subScore;
    }

    public float getObjScore() {
        return objScore;
    }

    public void setObjScore(Float objScore) {
        this.objScore = objScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	
    public int compareTo(SubScore ss) {
        if (this.getScore() > ss.getScore()) {
            return -1;
        } else if (this.getScore() < ss.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

	public List<SubChildScore> getSubCSL() {
		return subCSL;
	}

	public void setSubCSL(List<SubChildScore> subCSL) {
		this.subCSL = subCSL;
	}

	@Override
	public String toString() {
		return "SubScore [id=" + id + ", stuId=" + stuId + ", egsId=" + egsId + ", subName=" + subName + ", score="
				+ score + ", subScore=" + subScore + ", objScore=" + objScore + ", rank=" + rank + ", subCSL=" + subCSL
				+ "]";
	}

}
