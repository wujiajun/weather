package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Dt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5921769446839655576L;
	private int dow;
	private String sr;
	private String ss;
	private String date;
	private String hwd;
	private String lwd;
	private String wl;
	private String wdir;
	private int ltmp;
	private int htmp;
	private int id;
	private int lwid;
	private int hwid;

	@XmlAttribute
	public int getDow() {
		return dow;
	}

	public void setDow(int dow) {
		this.dow = dow;
	}

	@XmlAttribute
	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	@XmlAttribute
	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	@XmlAttribute
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlAttribute
	public String getHwd() {
		return hwd;
	}

	public void setHwd(String hwd) {
		this.hwd = hwd;
	}

	@XmlAttribute
	public String getLwd() {
		return lwd;
	}

	public void setLwd(String lwd) {
		this.lwd = lwd;
	}

	@XmlAttribute
	public String getWl() {
		return wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

	@XmlAttribute
	public String getWdir() {
		return wdir;
	}

	public void setWdir(String wdir) {
		this.wdir = wdir;
	}

	@XmlAttribute
	public int getLtmp() {
		return ltmp;
	}

	public void setLtmp(int ltmp) {
		this.ltmp = ltmp;
	}

	@XmlAttribute
	public int getHtmp() {
		return htmp;
	}

	public void setHtmp(int htmp) {
		this.htmp = htmp;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public int getHwid() {
		return hwid;
	}

	public void setHwid(int hwid) {
		this.hwid = hwid;
	}

	@XmlAttribute
	public int getLwid() {
		return lwid;
	}

	public void setLwid(int lwid) {
		this.lwid = lwid;
	}

}
