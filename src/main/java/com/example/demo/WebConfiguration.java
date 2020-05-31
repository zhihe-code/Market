package com.example.demo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	/**
	 * 把拦截器作为Bean让Spring管理，以防止Spring无法找到
	 * @return
	 */
	@Bean
	public CheckUserInterceptor checkUserInterceptor() {
		return new CheckUserInterceptor();
	}

	/**
	 * 注册自定义的拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(checkUserInterceptor());
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new Formatter <LocalDate>() {
			/*
			 * 将日期转换成字符串
			 * @param object 日期数据
			 * @param locale 本地化数据
			 */

			@Override
			public String print(LocalDate object, Locale locale) {
				//定义一个格式化对象
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd",locale);
				//调用格式化器的format方法把日期转换成字符串
				return dtf.format(object);
			}
			
			/*
			 * 字符串转换成日期
			 * @param text 格式:2020-04-19的字符串
			 * @param locale 本地化
			 * @return 日期
			 * @throws ParseException 如果提供的text格式不正确抛出异常
			 */

			@Override
			public LocalDate parse(String text, Locale locale) throws ParseException {
				return LocalDate.parse(text);//调用parse把字符串解释为日期对象
			}});
		WebMvcConfigurer.super.addFormatters(registry);
	}
	
	@Bean
	public Utils utils() {
		return new Utils();
	}
	

}
