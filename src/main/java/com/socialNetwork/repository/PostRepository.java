package com.socialNetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialNetwork.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("SELECT post FROM Post post "
			+ "LEFT JOIN post.user.friendships1 friendship "
			+ "WHERE post.user.id IN "
			+ "(SELECT user.id FROM User user WHERE (friendship.user1.id = post.user.id AND friendship.user2.id = :userId)) "
			+ "ORDER BY post.datePosted DESC")
	List<Post> findFriendsPosts(@Param("userId") Integer userId);
	
}
