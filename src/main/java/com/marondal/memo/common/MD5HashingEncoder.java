package com.marondal.memo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashingEncoder {

	
	public static String encode(String message) { // 객체 생성없이도 독립적으로 언제든 메소드를 사용가능 (static 사용)
		
		String result = "";
		try {
			MessageDigest messageDigest =  MessageDigest.getInstance("md5");
		
			byte[] bytes = message.getBytes();
		
			messageDigest.update(bytes);
			
			byte[] digest = messageDigest.digest();
			
			// 바이트 형식을 16진수로 문자열 형태로 바꿔줄것이다.
			for(int i = 0; i < digest.length; i++) {
				// 01010111
				// 11011101
				// 01010101 논리 연산
				// 한개한개의 문자열을 이어서 붙여준다
				result += Integer.toHexString(digest[i] & 0xff);
			}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return result;
		
	}
	
}
