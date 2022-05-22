package com.socialNetwork.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socialNetwork.dto.PostLikeDB;
import com.socialNetwork.dto.UserDB;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.PostLikePK;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostLikeRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;

@Controller
public class PostLikeController {

	@Autowired private PostLikeRepository postLikeRepo;
	@Autowired private PostRepository postRepo;
	
	@Autowired private UserService userService;

	public PostLikeDB save(PostLikeDB postLikeDB, String username) {
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		PostLike postLike = new PostLike();
		
		postLike.setDateWhen(now);
		postLike.setUser(loggedUser);
		
		Post post = postRepo.getById(postLikeDB.getFkPost().getId());
		postLike.setPost(post);
		
		PostLikePK postLikePK = new PostLikePK(loggedUser.getId(), post.getId());
		
		postLike.setId(postLikePK);
		
		postLikeRepo.save(postLike);
		
		postLikeDB.setDateWhen(postLike.getDateWhen());
		postLikeDB.setFkUser(new UserDB(loggedUser));
		
		return postLikeDB;
	}
	
	public void delete(Integer userId, Integer postId) {
		PostLike postLike = postLikeRepo.getById(new PostLikePK(userId, postId));
		
		postLikeRepo.delete(postLike);
	}
	
}
