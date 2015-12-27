package com.weather.core.bean;

public enum DeviceName {
	IPHONE("iphone"), ANDROID("android");

	private String name;

	private DeviceName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static DeviceName getDeviceName(String name) {
		for (DeviceName deviceName : values()) {
			if (deviceName.getName().equals(name)) {
				return deviceName;
			}
		}
		return null;
	}
}
