package com.marondal.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {

	// 상수(고정된 값) 상수로 지정되면 이 값이 절대로 다른곳에서 수정될수 없음
	// static을 붙임으로써 객체 생성 없이 쓸수 있는 멤버 변수가 됨
	// 실제 파일을 사용자가 사이트에 올렸을때 실제 파일을 전달하고 그 경로또한 전달하는 과정에서 주는거다
	// 이렇게 static 붙여줘야 밑에 static메소드에서 쓸수 있음 없으면 에러뜸
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\이중보\\Desktop\\코딩\\springProject\\upload\\memo";
	
	// 이건 규칙만 만든건지 우리가 설정해놓는게 아님 설정은 완전히 추가로 필요함 WebMvcConfig 클래스 에서 해야함 (그냥 스프링스 프로젝트에서 쓰라고 해놓음)
	// 파일 저장
	public static String saveFile(int userId, MultipartFile file) {
		
		if(file == null) { // 이미지가 null이 들어가도 controller에서 작동하게끔
			return null;
		}
		
		// 파일이름을 구분해주기 위함
		// 같은 파일이름으로 전달 될 경우
		// 폴더를 만들어서 파일 저장(같은 이름으로 전달되어도 폴더를 통해 관리)
		// 로그인된 사용자 userId를 기준으로 사용자기준으로 폴더이름을 지정
		// 현재 시간정보를 폴더 이름으로 저장(같은 사용자여도 구분할 수있게)
		// UNIX TIME(UNIX운영체제임) : 1970년 1월 1부터 흐른 시간을 milli second (1 / 1000초)로 표현한 값 (1000분의 1초안에 넣지 않은 이상 다른 파일로 인식)
		// ex) 2_938091328
		// userId를 그냥 메소드로 받아와 wjwkd
		// 폴터이름을 정하는것
		String directoryName = "/" + userId + "_" + System.currentTimeMillis(); // memo안에 이렇게 제목이 들어감 기본 경로외엔 / 이걸로 구분
		
		
		// 폴더 생성(내부적으론 그냥 이게 파일)
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(directoryPath); // 파일이라는 객체가 있는데 거기중에 경로를 넣어준거 // 이 파일객체는 파일에 관해 여러가지를 담아줄수있다
		
		if(!directory.mkdir()) {// mk make의 약자 // 개발자들이 파일 다룰때 아주 조심함 왜냐하면 외부적으로 그 파일형태를 못쓰게 하는 경우도 있어서 그럴경우에는 null을 return할수 있게 해줌
			// 여기안에 들어왔다는 건 폴더 생성 실패
			return null;
		}
		// 이외에는 파일저장
		// 이거 자체는 파일원본의 경로를 정해준것(C:\\Users\\이중보\\Desktop\\코딩\\springProject\\upload\\memo) 여기까지의 경로만 정해준거
		String filePath = directoryPath + "/" + file.getOriginalFilename(); // 전체 경로임
		
		
		try {
			
			byte[] bytes = file.getBytes();// 이게 실제 파일 객체 알맹이임 // 010101을 byte로 바꿔주는 역할
			Path path = Paths.get(filePath); // 파일경로의 문자열 객체로 관리
			Files.write(path, bytes); // 경로와 실제 파일을 저장
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		} 
		
		
		// 저장된 파일을 접근할 URL path 만들기(문자열로) 지금여기서 파일을 저장한 경로를 갖고있으니 그 경로를 접근할수 있는 URL 경로 만들기 그래야 Service에서 써줄수 있음
		// 파일 저장 경로 : C:\\Users\\이중보\\Desktop\\코딩\\springProject\\upload\\memo/2_8120980/test.png
		// URL path : /images/2_8120980/test.png 내가 이런 경로로 규칙을 만드는거
		return "/images" + directoryName + "/" + file.getOriginalFilename(); // 이런 규칙을 만들고 설정에 고대로 적용 // 이건 그건 규칙만 만드는거 
		// 이 리턴값은 파일하고 이름이 동일해야 브라우저에서 그 어떤 폴더에 있는지 알아내서 그 파일값을 갖고올수 있게끔 하기 위한 리턴값
		// 그래서 "/" + file.getOriginalFilename(); 여기에서 "/"를 반드시 붙여줘야하는것
		// "2_8120980" 이게 폴더를 의함 즉,"directoryName"이게 폴더이름을 얘기하는거고 거기에 /를 붙여야 구분이 가므로
	}
	
}
