package io.skhu.data.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.skhu.data.domain.User;
import io.skhu.data.repository.UserRepository;
import io.skhu.data.util.Encryption;
@Service
public class UserService {
	@Autowired UserRepository userRepository;
	public User login(String loginId, String password) {
		User tempUser=userRepository.findByLoginId(loginId);
		if(tempUser==null) return null;
		String pw=Encryption.encrypt(password, Encryption.MD5);
		if(!tempUser.getPassword().equals(pw)) return null;
		return tempUser;
	}
	public User findByToken(String token) throws UnsupportedEncodingException, ServletException {
		User findUser = null;
		if(token!="" && token!=null) {
	        String[] split_string = token.split("\\.");
	        String base64EncodedBody = split_string[1];
	        Base64 base64Url = new Base64(true);
	        String body = new String(base64Url.decode(base64EncodedBody), "UTF-8");
	        try {
				HashMap<String, Object> result = new ObjectMapper().readValue(body, HashMap.class);
				Date currentTime=new Date();
				int expTime=(int) result.get("exp");
				if(expTime < currentTime.getTime()/1000) {
					throw new ServletException("유효 시간이 만료되었습니다. 다시 로그인을 진행하시길 바랍니다.");
				}
				return userRepository.findByNickName((String) result.get("nickName"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			throw new ServletException("유효하지 않은 토큰입니다. 다시 진행하시길 바랍니다.");
		}
		return findUser;
	}
}
