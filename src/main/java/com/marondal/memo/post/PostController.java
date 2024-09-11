package com.marondal.memo.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marondal.memo.post.domain.Post;
import com.marondal.memo.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	
	@GetMapping("/list-view")
	public String memoList(
			Model model
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId"); // session(session에서 지금 로그인 되어있는 user를 관리하고 있으니)
														      // 객체에서 userId를 갖고와 지금 로그인되어있는 사람이 자기꺼만 볼수 있게 가능
		
		List<Post> postList= postService.getPostList(userId);
		
		model.addAttribute("memoList", postList);
		
		return "post/list";
	}
	
	@GetMapping("/create-view")
	public String inputMemo() {
		return "post/create";
	}
	
	@GetMapping("/detail-view")
	public String memoDetail(@RequestParam("id") int id, Model model) { // 메모 상세는 애초 화면 구성부터 어떤 메모내용인지 입력을 받아야함
																		// Model model 메모 상세 화면에서 타임리프를 쓰기 위해
		Post post = postService.getPost(id);
		
		model.addAttribute("memo", post);
		
		return "post/detail";
	}
	
}
