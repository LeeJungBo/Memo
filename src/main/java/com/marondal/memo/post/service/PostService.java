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
	
	public Post updatePost(int id, String title, String contents) {
		
		Optional<Post> optionalPost = postRepository.findById(id);
		
		Post post = optionalPost.orElse(null);
		
		
		if(post != null) {
			Post updatePost = post.toBuilder() // toBuilder는 update의 Jpa에서 있음
							  .title(title)
							  .contents(contents)
							  .build();
			
			return  postRepository.save(updatePost);
		}else {
			return null;
		}
		
		
		
	}
	
	public boolean deletePost(int id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		
		Post post = optionalPost.orElse(null); // optionalPost객체가 null이라면 그냥 post객체에 null이 담겨질것이다.
		
		if(post != null) { // null이 아니라면 
			FileManager.removeFile(post.getImagePath()); // null이 아닐때만 기능이 수행되도록 해야한다.
			postRepository.delete(post);
			return true; 
		}else {// 데드 콬드가 되어버린다. 따라서 그냥 nullexception발생시 그냥 에러가 생겨서 프로그램이 중단되어버림
			
			return false;
		}
		
//		FileManager.removeFile(post.getImagePath()); 이 이미파일을 먼저 지워버리면 post의 객체가 이상해지고(실제 파일은 없는데 파일경로만 데이터에 남아있는 이상한경우) 저밑에 코드가 데드코드가 되어버림 (else이후로)
//		따라서 post의 객체가 있을때만 FileManager.removeFile() 이 메소드가 작동되도록한다.
//		if(post != null) { // null이 아니라면 
//			postRepository.delete(post);
//			return true; 
//		}else {// 데드 콬드가 되어버린다. 따라서 그냥 nullexception발생시 그냥 에러가 생겨서 프로그램이 중단되어버림 즉,
//			
//			return false;
//		}
		
		
		
	}
	
	
	
}
