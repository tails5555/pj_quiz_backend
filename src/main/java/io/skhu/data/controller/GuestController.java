package io.skhu.data.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.skhu.data.controller.postDTO.AuthenticationRequest;
import io.skhu.data.domain.User;
import io.skhu.data.service.TokenAuthenticationService;
import io.skhu.data.service.UserService;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("guest")
public class GuestController {
	@Autowired UserService userService;
	@Autowired TokenAuthenticationService tas;
	@RequestMapping(value="user_login", method=RequestMethod.POST)
	public String login(@RequestBody AuthenticationRequest ar, HttpServletResponse response) throws IOException {
		User loginUser=userService.login(ar.getLoginId(), ar.getPassword());
		if(loginUser==null) return "";
		return tas.addAuthentication(response, loginUser);
	}
}
