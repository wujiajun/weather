package com.weather.core.bean;

public enum DataSource {
	MOJI("moji"), ZHIQU("zhiqu");
	private String name;

	private DataSource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static DataSource getDataSource(String name) {
		for (DataSource dataSource : values()) {
			if (dataSource.getName().equals(name)) {
				return dataSource;
			}
		}
		return null;
	}

}
