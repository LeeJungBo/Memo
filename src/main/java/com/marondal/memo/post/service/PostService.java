package com.marondal.memo.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marondal.memo.post.domain.Post;
import com.marondal.memo.post.repository.PostRepository;

@Service
public class PostService {

	
	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post addPost(int userId, String title, String contents) {
		// 로그인되어있어서 userId 갖고올수 있음(session을 통해)
		
		// userId는 어차피 로그인되어있어있어서(session)을 통해서 자동으로 얻어옴
		  Post post = Post.builder()
				  	  .userId(userId)
				  	  .title(title)
				  	  .contents(contents)
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
