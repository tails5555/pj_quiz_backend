package io.skhu.data.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.skhu.data.domain.ServiceBBSPost;
import io.skhu.data.domain.ServiceBBSTitle;

public interface ServiceBBSPostRepository extends JpaRepository<ServiceBBSPost, Integer>{
	List<ServiceBBSPost> findByServiceBBSTitleOrderByIdDesc(ServiceBBSTitle serviceBBSTitle);

	@Transactional
	@Modifying
	@Query("UPDATE ServiceBBSPost p SET p.views=p.views+1 WHERE p.id=:id")
	void updateViews(@Param("id") int id);
}
