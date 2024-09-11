package com.marondal.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.memo.post.domain.Post;
import com.marondal.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	private PostService postService;
	// @Autowired는 한개여서 생략되어있고 2개면 뭘 선택하는지에 대한 대상에 써줘야함
	public PostRestController(PostService postService) {
		this.postService = postService;
	}
	
	
	@PostMapping("/create")
	public Map<String, String>createMemo(
			@RequestParam("title") String title
			, @RequestParam("contents") String contents
			, @RequestParam(value="imageFile", required=false) MultipartFile file // 파일을 얻기위한것ㄱ // value="imageFile", required=false 필수가 아니어도 된다는 것을 어노테이션으로 설정
			, HttpSession session){// HttpSession session 이렇게 객체 생성없이 바로 받아올수 있게 사용법 만들어놓음
		
		// 이렇게 session을 바로쓸수 있음
		// HttpServletRequest session = request.getSession();
		// 전에 user쪽에서 로그인되어있는 상태이고 클래스에 있는 setAttribute로 userId가 저장되어있음
		// (userRestController)쪽에서 session(object객체로 잡고 있음)으로 잡고있음 즉, post는 그 로그인되어있는것을 바탕으로 session에서 userId을 가져다 써야함
		int userId = (Integer)session.getAttribute("userId"); // session.getAttribute("userId");이게 object객체로 뜸(문자열, 정수열 setAttribute에서 둘다 담을수 있음)
		
		Post post = postService.addPost(userId, title, contents, file); // 딱 그 로그인되어있는 user것만 보이게 해야하므로 userId필요
																  // userId는 session에서 끌고 왔기때문에 controller메소에서 요청받을 필요는 없음
		Map<String, String> resultMap = new HashMap<>();
		
		if(post != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
	
}
