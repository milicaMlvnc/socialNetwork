package com.socialNetwork.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socialNetwork.dto.UserDB;
import com.socialNetwork.dto.UserMessageDB;
import com.socialNetwork.entity.User;
import com.socialNetwork.entity.UserMessage;
import com.socialNetwork.repository.UserMessageRepository;
import com.socialNetwork.repository.UserRepository;
import com.socialNetwork.service.UserService;

@Controller
public class UserMessageController {

	@Autowired private UserMessageRepository userMessageRepo;
	@Autowired private UserRepository userRepo;
	
	@Autowired private UserService userService;
	
	public UserMessageDB send(UserMessageDB userMessageDB, String username) {
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		UserMessage userMessage = new UserMessage();
		
		userMessage.setDateWhen(now);
		userMessage.setTextMessage(userMessageDB.getTextMessage());
		
		userMessage.setUserSender(loggedUser);
		
		User userReceiver = userRepo.getById(userMessageDB.getFkUserReceiver().getId());
		userMessage.setUserReceiver(userReceiver);
		
//		UserMessagePK userMessagePK = new UserMessagePK(userMessage.getUserSender().getId(), userMessage.getUserReceiver().getId());
//		userMessage.setId(userMessagePK);
		
		userMessageRepo.save(userMessage);
		
		userMessageDB.setDateWhen(userMessage.getDateWhen());
		userMessageDB.setFkUserSender(new UserDB(userMessage.getUserSender()));
		
		return userMessageDB;
	}
	
	public List<UserMessageDB> getConversation (Integer userId, String username) {
		User loggedUser = userService.buildUser(username);
		
		List<UserMessage> conversation = userMessageRepo.findBySenderAndReceiver(loggedUser.getId(), userId);
		
		List<UserMessageDB> conversationDB = new ArrayList<>();
		for (UserMessage userMsg : conversation) {
			UserMessageDB userMsgDB = new UserMessageDB(userMsg);
			conversationDB.add(userMsgDB);
		}
		
		return conversationDB;
		
		
		// proveriti ko je ulogovan pa po tome vracati
//		List<UserMessage> conversation = userMessageRepo.getBySenderAndReceiver()
	}
	
}
