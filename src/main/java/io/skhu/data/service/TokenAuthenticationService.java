package io.skhu.data.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.skhu.data.domain.Person;
import io.skhu.data.domain.User;

@Service
public class TokenAuthenticationService {
	private long EXPIRATIONTIME=1000*60*60; // 1시간
	private String secret="secretkey";
	@Autowired PersonService personService;
	public String addAuthentication(HttpServletResponse response, User user) throws IOException {
		Claims claimList=Jwts.claims();
		Person userPerson=personService.findByUser(user);
		claimList.put("userName", userPerson.getName());
		claimList.put("nickName", user.getNickName());
		claimList.put("userType", user.getUserType());
		String jwt=Jwts.builder()
				.setSubject(user.getLoginId())
				.setClaims(claimList)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	             .signWith(SignatureAlgorithm.HS256, secret)
	             .compact();
		return jwt;
	}
}