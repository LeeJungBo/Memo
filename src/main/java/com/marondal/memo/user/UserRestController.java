package com.marondal.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marondal.memo.user.domain.User;
import com.marondal.memo.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {

	private UserService userService;
	
	// 이제 Autowired를 기본생성자를 통해 전달 받는다
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam("loginId") String loginId
			,@RequestParam("password") String password
			,@RequestParam("name") String name
			,@RequestParam("email") String email){
		
		int count = userService.addUser(loginId, password, name, email);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(count == 1) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpServletRequest request) {
		
		User user = userService.getUser(loginId, password);
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result", "success");
			
			// 로그인에 성공하면 session에 로그인성공 기록을 기록해줘야한다.
			// HttpServletRequest 객체로부터 얻어온다.	
			// 특정 클라이언트에서 사용될 session을 의미
			// 클라이언트의 session과 매칭되는건 그냥 내부에서 알아서 매칭
			HttpSession session = request.getSession();
			// key, value 형태의 데이터 관리
			// 로그인이 되었다는 정보를 저장
			// 어떤 페이지에서든 해당 정보를 사용할 수있다.(한번 로그인하면 로그아웃 하기전까지 계속 로그인 된 상태여야 하기때문이다)
			// 로그인된 사용자 정보를 저장해서 사용자 정보 기반의 페이지를 구성할 수 있다.(저 객체를 통해서 저장해놓고 어디서든 사용할수있다.)
			session.setAttribute("userId", user.getId()); // user.getId() 자바이니 자바문법 get으로 갖고오기
			session.setAttribute("userName", user.getName()); // 자주 사용되는 유저의 이름 또한 저장해놓는다. 
			
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
//	@GetMapping("/duplicate-id")
//	public Map<String, Boolean> isDuplicateId(@RequestParam("loginId") String loginId ){
//		
//		boolean isDuplicate = userService.isDuplicateId(loginId);
//		
//		Map<String, Boolean> resultMap = new HashMap<>();
//		
//		resultMap.put("isDuplicate", isDuplicate);
//	
//		return resultMap;
//	}
	
	
	
}
