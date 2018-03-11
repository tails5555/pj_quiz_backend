package io.skhu.data.controller.postDTO;

import lombok.Data;

@Data
public class AuthenticationRequest {
	String loginId;
	String password;
	public AuthenticationRequest() {

	}
	public AuthenticationRequest(String loginId, String password) {
		this.loginId=loginId;
		this.password=password;
	}
}
