package com.marondal.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.marondal.memo.common.FileManager;
import com.marondal.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // 이미지를 브라우저에 보여주기위해 설정하는 클래스

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH + "/"); //  윈도우에서는"///" /를 3개로 
		// 실제 브라우저 주소창에 그게 전달되어 화면에 뜨게끔하게 하기위한 설정 밑에 manager클래스는 그냥 규칙만 만들어놓음
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		PermissionInterceptor interceptor = new PermissionInterceptor();
		
		registry.addInterceptor(interceptor) // interceptor등록
		.addPathPatterns("/**")// 무조건 interceptor를 지나가는 보장은 없고 내가 어떤 경로만 거쳐가도록 등록시켜주는거 (물론 우리는 다 거쳐가도록 함) 이렇게 되면 내가 주소창에 직접 list주소를 넣어도 로그인 안되어있으면 로그인 페이지만 보임
		.excludePathPatterns("/static/**", "/images/**", "/user/logout"); // 얘네들은 interceptor자체를 타지않음 static즉 css같은애들은 탈 필요도 없도 images파일 역시 어차피 파일만 타면 되는거여서 interceptor탈 필요가 없음, 로그아웃도 마찬가지
		
	}

}
