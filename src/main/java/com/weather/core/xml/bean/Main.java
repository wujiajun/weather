package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "dts", "cc", "dws" })
public class Main implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6162817125633973898L;
	private Dts dts;
	private String id;
	private Cc cc;
	private Dws dws;

	public Main() {
		this.id = "main";
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public Dts getDts() {
		return dts;
	}

	public void setDts(Dts dts) {
		this.dts = dts;
	}

	@XmlElement
	public Cc getCc() {
		return cc;
	}

	public void setCc(Cc cc) {
		this.cc = cc;
	}

	@XmlElement
	public Dws getDws() {
		return dws;
	}

	public void setDws(Dws dws) {
		this.dws = dws;
	}
}
