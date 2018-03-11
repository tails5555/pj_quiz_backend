package io.skhu.data.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("userAPI")
public class UserController {
	@RequestMapping(value="token")
	public String authenticatedConfirm(@RequestParam("token") String token) throws ServletException, UnsupportedEncodingException{
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			throw new ServletException("유효하지 않은 토큰입니다. 다시 진행하시길 바랍니다.");
		}
		return token;
	}
}
