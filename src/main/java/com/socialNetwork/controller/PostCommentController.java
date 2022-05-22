package com.socialNetwork.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socialNetwork.dto.PostCommentDB;
import com.socialNetwork.dto.UserDB;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostCommentRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;

@Controller
public class PostCommentController {

	@Autowired private PostCommentRepository postCommentRepo;
	@Autowired private PostRepository postRepo;
	
	@Autowired private UserService userService;
	
	public PostCommentDB save(PostCommentDB postCommentDB, String username) {
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		PostComment postComment = new PostComment();
		
		boolean isUpdate = postCommentDB.getId() != null && postCommentDB.getId() != -1;
        if (isUpdate) {
        	postComment = postCommentRepo.getById(postCommentDB.getId());
        }
		
		postComment.setCommentText(postCommentDB.getCommentText());
		postComment.setDateWhen(now);
		postComment.setUser(loggedUser);
		
		Post post = postRepo.getById(postCommentDB.getFkPost().getId());
		postComment.setPost(post);
		
		postCommentRepo.save(postComment);
		
		postCommentDB.setDateWhen(postComment.getDateWhen());
		postCommentDB.setFkUser(new UserDB(loggedUser));
		
		return postCommentDB;
	}
	
	public void delete(Integer id) {
		PostComment postComment = postCommentRepo.getById(id);
		
		postCommentRepo.delete(postComment);
	}
	
	public List<PostCommentDB> allCommentsForPost(Integer postId) {
		Post post = postRepo.getById(postId);
		
		List<PostComment> postComments = post.getPostComments();
		
		List<PostCommentDB> postCommentsDB = new ArrayList<>();
		for (PostComment postComm : postComments) {
			PostCommentDB postCommDB = new PostCommentDB(postComm);
			postCommentsDB.add(postCommDB);
		}
		
		return postCommentsDB;
	}
	
}
