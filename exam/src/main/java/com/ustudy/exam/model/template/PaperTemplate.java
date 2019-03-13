package com.ustudy.exam.model.template;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jared
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PaperTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5272918138059804530L;

	private static final Logger logger = LoggerFactory.getLogger(PaperTemplate.class);
	
	@JsonProperty("CSID")
	private double egsid = 0;
	
	@JsonProperty("AnswerSheetXmlPath")
	private String paperPath = null;
	
	@JsonIgnore
	private List<PageTemplate> pagesL = null;
	
	@JsonProperty("TemplateInfo")
	private void parsePages(Map<String, Object> temp) throws Exception {
		// noted: elements in temp maybe unquoted fields
		ObjectMapper objM = new ObjectMapper();
		objM.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objM.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		logger.trace("parsePages(), TemplateInfo->" + temp.toString());
		String pgJson = objM.writeValueAsString(temp.get("pages"));
		System.out.println("jared->" + pgJson);
		objM.setSerializationInclusion(Include.NON_NULL);
		objM.setSerializationInclusion(Include.NON_EMPTY);
		pagesL = objM.readValue(pgJson, new TypeReference<List<PageTemplate>>(){});
		System.out.println("parsePages(), page size " + pagesL.size());
		logger.trace("parsePages(), page size " + pagesL.size());

	}

	public PaperTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getEgsid() {
		return egsid;
	}

	public void setEgsid(double egsid) {
		this.egsid = egsid;
	}

	public String getPaperPath() {
		return paperPath;
	}

	public void setPaperPath(String paperPath) {
		this.paperPath = paperPath;
	}

	public List<PageTemplate> getPagesL() {
		return pagesL;
	}

	public void setPagesL(List<PageTemplate> pagesL) {
		this.pagesL = pagesL;
	}

	/**
	 * @return accumulate file names in each page
	 * @throws Exception
	 */
	public String accumPageFilenames() throws Exception {
		String filen = null;
		if (pagesL != null && pagesL.size() > 0) {
			for (PageTemplate pg: pagesL) {
				String fn = pg.getFileName();
				if (fn == null || fn.isEmpty())
					throw new RuntimeException("accumPageFilenames(), empty file name in page " + pg.getPageIndex());
				if (filen == null || filen.isEmpty())
					filen = pg.getFileName();
				else
					filen += "," + pg.getFileName();
			}
		}
		return filen;
	}
	
	@Override
	public String toString() {
		return "PaperTemplate [egsid=" + egsid + ", paperPath=" + paperPath + ", pagesL=" + pagesL + "]";
	}
	
}
