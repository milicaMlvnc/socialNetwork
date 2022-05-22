package com.socialNetwork.grpc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.socialNetwork.entity.User;
import com.socialNetwork.entity.UserMessage;
import com.socialNetwork.repository.UserMessageRepository;
import com.socialNetwork.repository.UserRepository;
import com.socialNetwork.service.UserService;

import grpcPost.UserId;
import grpcPost.UserMessageList;
import grpcPost.UserMessageProto;
import grpcPost.UserProto;
import grpcPost.UserMessageServiceGrpc.UserMessageServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UserMessageServiceImpl extends UserMessageServiceImplBase {
	
	@Autowired UserService userService;
	@Autowired private UserMessageRepository userMessageRepo;
	@Autowired private UserRepository userRepo;

	@Override
	public void send(UserMessageProto received, StreamObserver<UserMessageProto> response) {
		User loggedUser = userService.buildUser(received.getUserSender().getUsername());
		Date now = new Date();
		
		UserMessage userMessage = new UserMessage();
		
		userMessage.setDateWhen(now);
		userMessage.setTextMessage(received.getTextMessage());
		userMessage.setUserSender(loggedUser);
		
		User userReceiver = userRepo.getById(received.getUserReceiver().getId());
		userMessage.setUserReceiver(userReceiver);
		
		userMessageRepo.save(userMessage);
		
		response.onNext(received);
		response.onCompleted();
	}
	
	@Override 
	public void getConversation(UserId received, StreamObserver<UserMessageList> response) {
		User loggedUser = userService.buildUser(received.getUsername());
		
		List<UserMessage> conversation = userMessageRepo.findBySenderAndReceiver(loggedUser.getId(), received.getId());
	
		List<UserMessageProto> conversationProto = new ArrayList<>();
		for (UserMessage userMsg : conversation) {
			UserMessageProto oneMsg = UserMessageProto.newBuilder().setId(userMsg.getId())
					.setTextMessage(userMsg.getTextMessage())
					.setUserSender(UserProto.newBuilder()
							.setId(userMsg.getUserSender().getId())
							.setUsername(userMsg.getUserSender().getUsername()))
					.setUserReceiver(UserProto.newBuilder()
							.setId(userMsg.getUserReceiver().getId())
							.setUsername(userMsg.getUserReceiver().getUsername()))
					.setDate(userMsg.getDateWhen().toString())
					.build();
			conversationProto.add(oneMsg);
		}
		
		UserMessageList res = UserMessageList.newBuilder().addAllMessages(conversationProto).build();
		
		response.onNext(res);
		response.onCompleted();
	}
}
