package com.weather.core.xml.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Idxs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7742714219281371816L;
	private long lupd;
	private List<Idx> idx;

	public Idxs() {
		this.lupd = System.currentTimeMillis();
	}

	@XmlAttribute
	public long getLupd() {
		return lupd;
	}

	public void setLupd(long lupd) {
		this.lupd = lupd;
	}

	@XmlElement
	public List<Idx> getIdx() {
		return idx;
	}

	public void setIdx(List<Idx> idx) {
		this.idx = idx;
	}

}
