package com.marondal.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.memo.common.FileManager;
import com.marondal.memo.post.domain.Post;
import com.marondal.memo.post.repository.PostRepository;

@Service
public class PostService {

	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post addPost(int userId, String title, String contents, MultipartFile file) {
		// 로그인되어있어서 userId 갖고올수 있음(session을 통해)
		
		// 따로 파일을 관리해주는 메소드를 빼줘서 관리
		String urlPath = FileManager.saveFile(userId, file); // 이런식으로 FileManager클래스에서 객체 생성없이 얻어와야함
		
		// userId는 어차피 로그인되어있어있어서(session)을 통해서 자동으로 얻어옴
		  Post post = Post.builder()
				  	  .userId(userId)
				  	  .title(title)
				  	  .contents(contents)
				  	  .imagePath(urlPath) // 이게 null이 되도 진행되게끔 manager에서 다 설정해놨음
				  	  .build();
		
		Post result = postRepository.save(post);
		
		return result;
	}
	
	public List<Post> getPostList(int userId){
		return postRepository.findByUserIdOrderByIdDesc(userId);
		
		
	}
	
	
	public Post getPost(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		
		Post post = optionalPost.orElse(null);
		
		return post;
	}
	
	
	
}
