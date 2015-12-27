package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Day implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1650467004871613777L;
	private int dow;
	private String date;
	private String hwd;
	private String lwd;
	private String hwl;
	private String hwdir;
	private int ltmp;
	private int htmp;
	private int id;
	private int hwid;
	private int lwid;
	private String lwl;
	private String lwdir;

	@XmlAttribute
	public int getDow() {
		return dow;
	}

	public void setDow(int dow) {
		this.dow = dow;
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
	public String getHwl() {
		return hwl;
	}

	public void setHwl(String hwl) {
		this.hwl = hwl;
	}

	@XmlAttribute
	public String getHwdir() {
		return hwdir;
	}

	public void setHwdir(String hwdir) {
		this.hwdir = hwdir;
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

	@XmlAttribute
	public String getLwl() {
		return lwl;
	}

	public void setLwl(String lwl) {
		this.lwl = lwl;
	}

	@XmlAttribute
	public String getLwdir() {
		return lwdir;
	}

	public void setLwdir(String lwdir) {
		this.lwdir = lwdir;
	}

}
