package com.socialNetwork.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socialNetwork.entity.Friendship;
import com.socialNetwork.entity.FriendshipPK;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.PostLikePK;
import com.socialNetwork.entity.User;
import com.socialNetwork.entity.UserMessage;
import com.socialNetwork.repository.FriendshipRepository;
import com.socialNetwork.repository.PostCommentRepository;
import com.socialNetwork.repository.PostLikeRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.repository.UserMessageRepository;
import com.socialNetwork.repository.UserRepository;
import com.socialNetwork.service.UserService;

@Controller
public class UtilController {

	@Autowired private UserMessageRepository userMessageRepo;
	@Autowired private UserRepository userRepo;
	@Autowired private PostLikeRepository postLikeRepo;
	@Autowired private PostRepository postRepo;
	@Autowired private PostCommentRepository postCommentRepo;
	@Autowired private FriendshipRepository friendshipRepo;
	
	public boolean fillWithPosts() {
		User user = userRepo.getById(100);
		for (int k = 32005; k < 100000; k++) {
			Date now = new Date();
			Post post = new Post();
			post.setDatePosted(now);
			post.setPostText("Post text " + k);
			post.setTitle("Title " + k);
			post.setUser(user);
			post = postRepo.save(post);
		}
		return true;
	}
	
	public Boolean fillDatabase() {
		Date now = new Date();
		for(int i = 3001; i <= 3200; i++) {
			// user
			User user = new User();
			user.setName("Name " + i);
			user.setMail("Mail " + i);
			user.setPass("Pass " + i);
			user.setUsername("username" + i);
			
			user = userRepo.save(user);
			// user
			
			// post
			for (int k = 0; k < 25; k++) {
				
				Post post = new Post();
				post.setDatePosted(now);
				post.setPostText("Post text " + k);
				post.setTitle("Title " + k);
				post.setUser(user);
				post = postRepo.save(post);
				// post
				
				if (i != 0 && i != 1) {
					for (int j = 0; j <= 3; j++) {
						if (j < i) {
							PostComment postComm = new PostComment();
							postComm.setDateWhen(now);
							postComm.setCommentText("Comment text " + j);
							postComm.setPost(post);
							
							Random random = new Random();
						    Integer randomInt = random.ints(0, i - 1)
						      .findFirst()
						      .getAsInt();
							
							User postCommUser = userRepo.findUserByUsername("username" + randomInt).get();
							postComm.setUser(postCommUser);
							postComm.setUser(postCommUser);
							
							postCommentRepo.save(postComm);
						}
					}
					for (int j = 0; j <= 3; j++) {
						if (j < i) {
							PostLike postLike = new PostLike();
							postLike.setDateWhen(now);
							postLike.setPost(post);
							
							Random random = new Random();
						    Integer randomInt = random.ints(0, i - 1)
						      .findFirst()
						      .getAsInt();
							
							User postLikeUser = userRepo.findUserByUsername("username" + randomInt).get();
							postLike.setUser(postLikeUser);
							
							postLike.setId(new PostLikePK(postLikeUser.getId(), post.getId()));
							
							postLikeRepo.save(postLike);
						}
					}
				}
			}
			
			// friendship
			if (i != 0 && i != 1) {
				for (int j = 0; j <= 3; j++) {
					if (j < i) {
						Random random = new Random();
					    Integer randomInt = random.ints(0, i - 1)
					      .findFirst()
					      .getAsInt();
					    
						Friendship friendship1 = new Friendship();
						friendship1.setUser1(user);
						
						User friendUser = userRepo.findUserByUsername("username" + randomInt).get();
						friendship1.setUser2(friendUser);
						friendship1.setDateWhen(now);
						friendship1.setId(new FriendshipPK(user.getId(), friendUser.getId()));
						friendshipRepo.save(friendship1);
						
						Friendship friendship2 = new Friendship();
						friendship2.setUser2(user);
						friendship2.setUser1(friendUser);
						friendship2.setDateWhen(now);
						friendship2.setId(new FriendshipPK(friendUser.getId(), user.getId()));
						friendshipRepo.save(friendship2);
					}
				}
			}
			// friendship
			
			// Message
			if (i != 0 && i != 1) {
				for (int j = 0; j <= 10; j++) {
					if (j < i) {
						UserMessage userMessage = new UserMessage();
						userMessage.setDateWhen(now);
						userMessage.setTextMessage("Text message " + j);
						userMessage.setUserSender(user);
						
						Random random = new Random();
					    Integer randomInt = random.ints(0, i - 1)
					      .findFirst()
					      .getAsInt();
						
						User userReceiver = userRepo.findUserByUsername("username" + randomInt).get();
						userMessage.setUserReceiver(userReceiver);
						
						userMessageRepo.save(userMessage);
					}
				}
			}
			// Message
		}
		
		return true;
	}
	
}
