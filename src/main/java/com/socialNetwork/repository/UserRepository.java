package com.socialNetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.socialNetwork.entity.Friendship;
import com.socialNetwork.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findUserByUsername(@Param("username")String username);

	@Query("SELECT user.friendships2 from User user where user.username like 'f'")
	List<Friendship> proba();
	
}
