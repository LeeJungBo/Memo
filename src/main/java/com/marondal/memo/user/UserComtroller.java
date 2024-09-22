package com.marondal.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserComtroller {
	
	@GetMapping("/join-view")
	public String inputJoin() {
		return "user/join";
	}
	
	@GetMapping("/login-view")
	public String inputLogin() {
		return "user/login";
	}
	
	@GetMapping("/logout") // session객체에 있는 정보를 지우면 된다.
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		
		session.removeAttribute("userId");
		session.removeAttribute("userName"); // session객체에 있는 userId,userName 지유기
		
		return "redirect:/user/login-view"; // 로그아웃은 그냥 간단히 할수 있는 처리라 여기서 그냥 redirect
		
	}
	
}
