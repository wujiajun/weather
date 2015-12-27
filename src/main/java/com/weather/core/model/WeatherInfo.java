package com.weather.core.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WeatherInfo {

	private List<FutureWeather> futureWeathers;
	private List<WarningWeather> warningWeathers;
	private BaseWeather yesterdayBaseWeather;
	private BaseWeather baseWeather;
	// private String cityName;// 城市名字只在今天的天气数据里获取
	// private String dayIcon;
	private String date;// 时间 2013/03/29
	// private String description;// 天气描述 厚厚云层笼罩下的低温天气，外出请注意保暖
	private String sunrise;// 日出时间
	private String sunset;// 日落时间
	// private String lastFestival;// 距离最近的节日1|愚人节;1(前面一个1不知道什么意思.看|后面的数据
	// 距离愚人节还有
	// 1天)
	// private String daytimeWindPower;// 白天风力
	// private String nightWindPower;// 晚上风力
	// private String daytimeWindDirection;// 白天风向
	// private String nightWindDirection;// 晚上风向
	// private String sky; // 天气
	private Air air;//
	// private String lunarCalendar;// 农历日期癸巳年二月十八
	private Date updateTime;// 发布时间
	private Integer nowTmp;// 实时温度
	private Integer hum;// 湿度
	private List<ForecastHour> forecastHours;
	private Integer uvidxLv;
	private String uvidx;
	private Integer makeupLv;
	private String makeup;
	private Integer weekDay;

	public WeatherInfo() {
	}

	public Integer getUvidxLv() {
		return uvidxLv;
	}

	public String getUvidx() {
		return uvidx;
	}

	public void setUvidx(String uvidx) {
		this.uvidx = uvidx;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getNowTmp() {
		return nowTmp;
	}

	public void setNowTmp(Integer nowTmp) {
		this.nowTmp = nowTmp;
	}

	public Integer getHum() {
		return hum;
	}

	public void setHum(Integer hum) {
		this.hum = hum;
	}

	public void setUvidxLv(Integer uvidxLv) {
		this.uvidxLv = uvidxLv;
	}

	public Air getAir() {
		return air;
	}

	public void setAir(Air air) {
		this.air = air;
	}

	public List<ForecastHour> getForecastHours() {
		return forecastHours;
	}

	public void setForecastHours(List<ForecastHour> forecastHours) {
		this.forecastHours = forecastHours;
	}

	public List<FutureWeather> getFutureWeathers() {
		return futureWeathers;
	}

	public void setFutureWeathers(List<FutureWeather> futureWeathers) {
		this.futureWeathers = futureWeathers;
	}

	public BaseWeather getYesterdayBaseWeather() {
		return yesterdayBaseWeather;
	}

	public void setYesterdayBaseWeather(BaseWeather yesterdayBaseWeather) {
		this.yesterdayBaseWeather = yesterdayBaseWeather;
	}

	public BaseWeather getBaseWeather() {
		return baseWeather;
	}

	public void setBaseWeather(BaseWeather baseWeather) {
		this.baseWeather = baseWeather;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<WarningWeather> getWarningWeathers() {
		return warningWeathers;
	}

	public void setWarningWeathers(List<WarningWeather> warningWeathers) {
		this.warningWeathers = warningWeathers;
	}

	public Integer getMakeupLv() {
		return makeupLv;
	}

	public void setMakeupLv(Integer makeupLv) {
		this.makeupLv = makeupLv;
	}

	public String getMakeup() {
		return makeup;
	}

	public void setMakeup(String makeup) {
		this.makeup = makeup;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

}
