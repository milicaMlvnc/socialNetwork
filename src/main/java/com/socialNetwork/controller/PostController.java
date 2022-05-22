package com.socialNetwork.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socialNetwork.dto.PostDB;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;
import com.socialNetwork.utils.Utils;

@Controller
public class PostController {

	@Autowired private PostRepository postRepo;
	
	@Autowired private UserService userService;
	
	@Autowired private PostCommentController postCommentController;
	@Autowired private PostLikeController postLikeController;
	
	
	public List<PostDB> getPostsForUserFriends(String username) {
		User loggedUser = userService.buildUser(username);
		
		List<Post> posts = postRepo.findFriendsPosts(loggedUser.getId());
		
		
		List<PostDB> postsDB = new ArrayList<>();
		for (Post post : posts) {
			PostDB postDB = new PostDB(post);
			postsDB.add(postDB);
		}
		return postsDB;
	}
	
	public List<PostDB> getAll() {
		List<Post> posts = postRepo.findAll();
		
		List<PostDB> postsDB = new ArrayList<>();
		for (Post post : posts) {
			PostDB postDB = new PostDB(post);
			postsDB.add(postDB);
		}
		return postsDB;
	}
	
	public PostDB save (PostDB postDB, String username) {
    	User loggedUser = userService.buildUser(username);
        Date now = new Date();
        
        Post post = new Post();
		
		boolean isUpdate = postDB.getId() != null && postDB.getId() != -1;
        if (isUpdate) {
            post = postRepo.getById(postDB.getId());
        }
        
        post.setDatePosted(now);
        post.setPostText(postDB.getPostText());
        post.setTitle(postDB.getTitle());
        post.setUser(loggedUser);
        
        post = postRepo.saveAndFlush(post);
        postDB.setId(post.getId());

        return postDB;
	}
	
	@Transactional
	public void delete(Integer id) {
		Post post = postRepo.getById(id);
		
		if (!Utils.isEmpty(post.getPostComments())) {
			for (PostComment postComm : post.getPostComments()) {
				postCommentController.delete(postComm.getId());
			}
		}
		
		if (!Utils.isEmpty(post.getPostLikes())) {
			for (PostLike postLike : post.getPostLikes()) {
				postLikeController.delete(postLike.getPost().getId(), postLike.getUser().getId());
			}
		}
		
		postRepo.delete(post);
	}
	
	public PostDB getOne(Integer id) {
		Post post = postRepo.getById(id);
		
		return new PostDB(post);
	}
	
}
