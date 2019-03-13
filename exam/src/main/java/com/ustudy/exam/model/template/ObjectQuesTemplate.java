package com.ustudy.exam.model.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ObjectQuesTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4692641791616101231L;
	
	@JsonProperty("topicType")
	private int quesType = -1;
	
	@JsonIgnore
	private List<Integer> quesNoL = null;

	private QuesRegion region = null;
	
	public ObjectQuesTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("numList")
	private void parseObjectQuesNo(List<Map<String, Object>> numL) throws Exception {
		System.out.println(numL.toString());
		if (numL != null && !numL.isEmpty()) {
			quesNoL = new ArrayList<Integer>();
			for (Map<String, Object> obj: numL) {
				Integer key = Integer.valueOf(obj.get("Key").toString());
				if (key == null || key.intValue() < 1)
					throw new RuntimeException("parseObjectQuesNo(), invalid key value " + key);
				quesNoL.add(key);
			}
		}
		System.out.println(quesNoL.toString());
	}
	
	public int getQuesType() {
		return quesType;
	}

	public void setQuesType(int quesType) {
		this.quesType = quesType;
	}
	
	public QuesRegion getRegion() {
		return region;
	}

	public void setRegion(QuesRegion region) {
		this.region = region;
	}

	public List<Integer> getQuesNoL() {
		return quesNoL;
	}

	public void setQuesNoL(List<Integer> quesNoL) {
		this.quesNoL = quesNoL;
	}

	@Override
	public String toString() {
		return "ObjectQuesTemplate [quesType=" + quesType + ", quesNoL=" + quesNoL + ", region=" + region + "]";
	}

}
