package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubScore implements Serializable, Comparable<SubScore> {

    private static final long serialVersionUID = -1704696113960776084L;

    private Long id;
    private Long stuId;
    private Long egsId;
    private String subName = null;
	private Float score;
    private Float subScore;
    private Float objScore;
    private int rank;
    
    @JsonProperty("childScores")
    private List<SubChildScore> subCSL = null;

    public SubScore() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public SubScore(String subName, Float score, int rank) {
		super();
		this.subName = subName;
		this.score = score;
		this.rank = rank;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuid) {
        this.stuId = stuid;
    }

    public Long getEgsId() {
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

    public Float getSubScore() {
        return subScore;
    }

    public void setSubScore(Float subScore) {
        this.subScore = subScore;
    }

    public Float getObjScore() {
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
