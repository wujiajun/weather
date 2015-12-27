package com.weather.core.model;

import org.springframework.util.StringUtils;

public class BaseWeather {
	private String hwd;
	private String lwd;
	private String skyId;
	private Integer minTmp;// 最小气温
	private Integer maxTmp;// 最大气温
	private String desc;
	private String wdir;
	private String wl;

	public String getHwd() {
		return hwd;
	}

	public void setHwd(String hwd) {
		this.hwd = hwd;
	}

	public String getLwd() {
		return lwd;
	}

	public void setLwd(String lwd) {
		this.lwd = lwd;
	}

	public String getSkyId() {
		return skyId;
	}

	public void setSkyId(String skyId) {
		this.skyId = skyId;
	}

	public Integer getMinTmp() {
		return minTmp;
	}

	public void setMinTmp(Integer minTmp) {
		this.minTmp = minTmp;
	}

	public void setMinTmpStr(String minTmp) {
		if (StringUtils.hasText(minTmp)) {
			this.minTmp = Float.valueOf(minTmp).intValue();
		}
	}

	public Integer getMaxTmp() {
		return maxTmp;
	}

	public void setMaxTmp(Integer maxTmp) {
		this.maxTmp = maxTmp;
	}

	public void setMaxTmpStr(String maxTmp) {
		if (StringUtils.hasText(maxTmp)) {
			this.maxTmp = Float.valueOf(maxTmp).intValue();
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getWdir() {
		return wdir;
	}

	public void setWdir(String wdir) {
		this.wdir = wdir;
	}

	public String getWl() {
		return wl;
	}

	public void setWl(String wl) {
		this.wl = wl;
	}

}
