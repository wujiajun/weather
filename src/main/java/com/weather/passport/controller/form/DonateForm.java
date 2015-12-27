package com.weather.passport.controller.form;

public class DonateForm {
	private String udid;
	private String name;
	private int price;
	private int colorIndex;
	private String city;
	private String orderId;
	private String origin;
	private String alipayParams;

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getAlipayParams() {
		return alipayParams;
	}

	public void setAlipayParams(String alipayParams) {
		this.alipayParams = alipayParams;
	}
}
