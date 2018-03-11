package io.skhu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.ServiceBBSTitle;

public interface ServiceBBSTitleRepository extends JpaRepository<ServiceBBSTitle, Integer>{
	ServiceBBSTitle findBySubDomain(String subDomain);
}
