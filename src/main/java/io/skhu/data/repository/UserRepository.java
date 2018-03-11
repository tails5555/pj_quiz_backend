package io.skhu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByLoginId(String loginId);
	User findByNickName(String nickName);
}
