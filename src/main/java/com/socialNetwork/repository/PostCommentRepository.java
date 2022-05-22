package com.socialNetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialNetwork.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer>{

	@Query("SELECT post.postComments FROM Post post WHERE post.id = :postId")
	List<PostComment> getCommentsForPost(@Param("postId")Integer postId);
	
}
