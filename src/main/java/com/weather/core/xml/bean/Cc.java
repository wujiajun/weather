package com.weather.core.xml.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class Cc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2050793213090958857L;
	private String gdt;
	private String upt;
	private String tmp;
	private String hum;
	private String ss;
	private String sr;
	private String wl;
	private String wdir;
	private String htmp;
	private String ltmp;
	private String wd;
	private String wid;

	@XmlAttribute
	public String getGdt() {
		return gdt;
	}

	public void setGdt(String gdt) {
		this.gdt = gdt;
	}

	@XmlAttribute
	public String getUpt() {
		return upt;
	}

	public void setUpt(String upt) {
		this.upt = upt;
	}

	@XmlAttribute
	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	@XmlAttribute
	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	@XmlAttribute
	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	@XmlAttribute
	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	@XmlAttribute
	public String getWl() {
		return wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

	@XmlAttribute
	public String getHtmp() {
		return htmp;
	}

	@XmlAttribute
	public String getWdir() {
		return wdir;
	}

	public void setWdir(String wdir) {
		this.wdir = wdir;
	}

	public void setHtmp(String htmp) {
		this.htmp = htmp;
	}

	@XmlAttribute
	public String getLtmp() {
		return ltmp;
	}

	public void setLtmp(String ltmp) {
		this.ltmp = ltmp;
	}

	@XmlAttribute
	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	@XmlAttribute
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

}
