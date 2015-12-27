package com.weather.core.spider.service;


public interface IParser<T> {
	T parser(String content);
}
