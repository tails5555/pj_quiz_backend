package io.skhu.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import io.skhu.data.config.JwtFilter;

@SpringBootApplication
public class PjQuizBackApplication {
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/userAPI/*");
		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(PjQuizBackApplication.class, args);
	}
}
