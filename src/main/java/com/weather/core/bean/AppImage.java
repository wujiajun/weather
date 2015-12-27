package com.weather.core.bean;

public enum AppImage {
	YIJING("yijing", "", "350k", "weather.backpic.yijing",
			"http://static.51juzhai.com/yijing.jpg"), JIHE("jihe",
			"http://static.51juzhai.com/appimage/zip/jihe.zip", "408k",
			"weather.backpic.jihe", "http://static.51juzhai.com/jihe.jpg"), ZIRAN(
			"ziran", "http://static.51juzhai.com/appimage/zip/ziran.zip",
			"725", "weather.backpic.ziran",
			"http://static.51juzhai.com/ziran.jpg"), LONGMAO("longmao",
			"http://static.51juzhai.com/appimage/zip/longmao.zip", "790k",
			"weather.backpic.longmao", "http://static.51juzhai.com/longmao.jpg"), TIANLU(
			"tianlu", "http://static.51juzhai.com/appimage/zip/tianlu.zip",
			"938k", "weather.backpic.tianlu",
			"http://static.51juzhai.com/tianlu.jpg"), YEZI("yezi",
			"http://static.51juzhai.com/appimage/zip/yezi.zip", "517k",
			"weather.backpic.yezi", "http://static.51juzhai.com/yezi.jpg"), FANGAO(
			"fangao", "http://static.51juzhai.com/appimage/zip/fangao.zip",
			"1.83M", "weather.backpic.fangao",
			"http://static.51juzhai.com/fangao.jpg"), ZHIGAN("zhigan",
			"http://static.51juzhai.com/appimage/zip/zhigan.zip", "590k",
			"weather.backpic.zhigan", "http://static.51juzhai.com/zhigan.jpg"), SHUIGUO(
			"shuiguo", "http://static.51juzhai.com/appimage/zip/shuiguo.zip",
			"0.98M", "weather.backpic.shuiguo",
			"http://static.51juzhai.com/shuiguo.jpg"), DOUDOU("doudou",
			"http://static.51juzhai.com/appimage/zip/doudou.zip", "315k",
			"weather.backpic.doudou", "http://static.51juzhai.com/doudou.jpg"), JIANZHU(
			"jianzhu", "http://static.51juzhai.com/appimage/zip/jianzhu.zip",
			"918k", "weather.backpic.jianzhu",
			"http://static.51juzhai.com/jianzhu.jpg");
	public String getFilename() {
		return filename;
	}

	private String filename;
	private String downLoadLink;
	private String name;
	private String size;
	private String preview;

	private AppImage(String filename, String downLoadLink, String size,
			String name, String preview) {
		this.filename = filename;
		this.downLoadLink = downLoadLink;
		this.size = size;
		this.name = name;
		this.preview = preview;
	}

	public String getDownLoadLink() {
		return downLoadLink;
	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public String getPreview() {
		return preview;
	}

}
