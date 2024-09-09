package com.marondal.memo.user.service;

import org.springframework.stereotype.Service;

import com.marondal.memo.common.MD5HashingEncoder;
import com.marondal.memo.user.domain.User;
import com.marondal.memo.user.repository.UserRepository;

@Service
public class UserService {
	
	
	private UserRepository userRepository;
	
	// 생성자가 autowired를 위한 것 하나만 존재하는 경우 autowired 생략 만약 다른 2가지라면 Autowired로 누구를 해줄것인지 명시해줘야한다.
//	@Autowired
	public UserService(UserRepository userRepository){
		this.userRepository =  userRepository;
	}
	
	public int addUser(
			String loginId
			, String password
			, String name
			, String email){
		
		// 전달받은 데이터중에 비밀번호를 암호화해서 보내줌(암호화)
		String encryptPassword = MD5HashingEncoder.encode(password); // 객체 생성없이도 메소드 호출 가능 static으로 해줌
		
		return userRepository.insertUser(loginId, encryptPassword, name, email);
		
	}
	
	public User getUser(String loginId, String password) { // 사용자 정보를 다 갖고와야한다.
		
		String encryptPassword = MD5HashingEncoder.encode(password);
		
		return userRepository.selectUser(loginId, encryptPassword);
	}
	
//	public boolean isDuplicateId(String loginId){
//		
//		int count = userRepository.selectCountByLoginId(loginId);
//		
//		if(count == 0) {
//			return false; // 0이면 중복 안됐다
//		}else {
//			return true;
//		}
//		
//	}
	
}
