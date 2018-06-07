package com.ustudy.exam.model.score;

import java.io.Serializable;

public class ExameeScore implements Serializable, Comparable<ExameeScore> {

    private static final long serialVersionUID = -1704696113960776084L;

    private Long id;
    private Long stuid;
    private Long examId;
    private Float score;
    private int rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuid() {
        return stuid;
    }

    public void setStuid(Long stuid) {
        this.stuid = stuid;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int compareTo(ExameeScore ss) {
        if (this.getScore() > ss.getScore()) {
            return -1;
        } else if (this.getScore() < ss.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

	@Override
	public String toString() {
		return "ExameeScore [id=" + id + ", stuid=" + stuid + ", examId=" + examId + ", score=" + score + ", rank="
				+ rank + "]";
	}

}
