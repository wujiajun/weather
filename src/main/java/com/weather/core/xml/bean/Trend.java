package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "trends", "history" })
public class Trend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5339812911971713760L;
	private Trends trends;
	private History history;
	private String id;

	public Trend() {
		this.id = "trend";
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public Trends getTrends() {
		return trends;
	}

	public void setTrends(Trends trends) {
		this.trends = trends;
	}

	@XmlElement
	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

}
