package io.skhu.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.ServiceBBSComment;
import io.skhu.data.domain.ServiceBBSPost;

public interface ServiceBBSCommentRepository extends JpaRepository<ServiceBBSComment, Integer>{
	public List<ServiceBBSComment> findByServiceBBSPostOrderByWrittenDateDesc(ServiceBBSPost serviceBBSPost);
}
