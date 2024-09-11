package com.marondal.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.marondal.memo.common.FileManager;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // 이미지를 브라우저에 보여주기위해 설정하는 클래스

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH + "/"); //  윈도우에서는"///" /를 3개로 
		// 실제 브라우저 주소창에 그게 전달되어 화면에 뜨게끔하게 하기위한 설정 밑에 manager클래스는 그냥 규칙만 만들어놓음
	}
}
