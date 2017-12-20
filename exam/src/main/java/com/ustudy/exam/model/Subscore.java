package com.ustudy.exam.model;

import java.io.Serializable;

public class Subscore implements Serializable, Comparable<Subscore> {

    private static final long serialVersionUID = -1704696113960776084L;

    private Long id;
    private Long stuid;
    private Long egsId;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int compareTo(Subscore ss) {
        if (this.getScore() > ss.getScore()) {
            return -1;
        } else if (this.getScore() < ss.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

}
