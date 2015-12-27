package com.weather.data.bean;

public enum Gender {
	BOY(1), GIRL(0);
	private int type;

	private Gender(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static Gender getGender(Integer type) {
		if (type == null)
			return null;

		for (Gender gender : values()) {
			if (gender.getType() == type) {
				return gender;
			}
		}
		return null;
	}
}
