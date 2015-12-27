package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "idxs", "air" })
public class Index implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5980176755598197308L;
	private String id;
	private AirTemplate air;
	private Idxs idxs;

	public Index() {
		this.id = "index";
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "air")
	public AirTemplate getAir() {
		return air;
	}

	public void setAir(AirTemplate air) {
		this.air = air;
	}

	@XmlElement(name = "idxs")
	public Idxs getIdxs() {
		return idxs;
	}

	public void setIdxs(Idxs idxs) {
		this.idxs = idxs;
	}

}
