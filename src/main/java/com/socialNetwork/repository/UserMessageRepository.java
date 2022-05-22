package com.socialNetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.socialNetwork.entity.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {

	@Query("SELECT userMsg FROM UserMessage userMsg "
			+ "WHERE (userMsg.userSender.id = :user1Id AND userMsg.userReceiver.id = :user2Id) "
			+ "OR (userMsg.userReceiver.id = :user1Id AND userMsg.userSender.id = :user2Id) "
			+ "ORDER BY userMsg.dateWhen DESC")
	List<UserMessage> findBySenderAndReceiver(Integer user1Id, Integer user2Id);

}
