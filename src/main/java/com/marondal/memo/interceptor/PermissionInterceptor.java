package com.marondal.memo.interceptor;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(
			HttpServletRequest request
			, HttpServletResponse response
			, Object handle) throws IOException {
		
		// 로그인이 안된 상태에서 메모리스트, 메모 입력화면, .... 게시글과 관련된 페이지로 접근을 막고
		// 로그인 페이지로 이동(에러뜨는것을 방지)
		
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId"); // Integer타입이 이거인 이유는 기존은 그냥 쓰기 위해서지만 지금은 있냐 없냐를 따질라는거여서 실제 데이터타입공간인 Integer로
		
		// /post/list-view 이런 형태
		String url = request.getRequestURI();
		
		if(userId == null) { // 로그인이 안된 상태
			// /post
			if(url.startsWith("/post")){
				// redirect
				response.sendRedirect("/user/login-view"); // 즉 userId가 없으면 그냥 자연스럽게 로그인페이지로 감
				return false; // false값이 되면 진행되지않음
			}
				
		}else { // 로그인 된 상태
			// 사용자와 관련된 페이지로 접근을 막고
			// 리스트 페이지로 이동
			if(url.startsWith("/user")){ // 로그인 페이지로 들어가는게 이상하면 그냥 리스트 페이지로 가기
				response.sendRedirect("/post/list-view");
				return false;
			}
			
		}
		
		return true;
		
		
	}
	
	
}
