package com.socialNetwork.graphql.resolver;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.PostLikePK;
import com.socialNetwork.entity.User;
import com.socialNetwork.entity.UserMessage;
import com.socialNetwork.repository.PostCommentRepository;
import com.socialNetwork.repository.PostLikeRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.repository.UserMessageRepository;
import com.socialNetwork.repository.UserRepository;
import com.socialNetwork.service.UserService;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;

@Component
public class MutationResolver implements GraphQLMutationResolver {
	
	@Autowired PostRepository postRepo;
	@Autowired UserRepository userRepo;
	@Autowired UserMessageRepository userMessageRepo;
	@Autowired PostCommentRepository postCommentRepo;
	@Autowired PostLikeRepository postLikeRepo;
	
	@Autowired private UserService userService;
	
	public Boolean deletePost(Integer id) {
    	Post post = postRepo.findById(id).get();
    	postRepo.delete(post);
    	
        return true;
    }
	
	public Post save(Integer id, String postText, String title, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		
		Post post = new Post();
		
		Date now = new Date();
		
		boolean isUpdate = id != -1;
        if (isUpdate) {
            post = postRepo.findById(id).get();
        }
        
        post.setDatePosted(now);
        post.setPostText(postText);
        post.setTitle(title);
        post.setUser(loggedUser);
        
        post = postRepo.saveAndFlush(post);
        
        return post;
	}
	
	public UserMessage sendMessage(String textMessage, Integer userReceiverId, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		UserMessage userMessage = new UserMessage();
		
		userMessage.setDateWhen(now);
		userMessage.setTextMessage(textMessage);
		userMessage.setUserSender(loggedUser);
		
		User userReceiver = userRepo.getById(userReceiverId);
		userMessage.setUserReceiver(userReceiver);
		
		userMessageRepo.saveAndFlush(userMessage);
		
		return userMessage;
	}
	
	public PostComment savePostComment(Integer id, String commentText, Integer postId, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		PostComment postComment = new PostComment();
		boolean isUpdate = id != -1;
        if (isUpdate) {
        	postComment = postCommentRepo.getById(id);
        }
		
		postComment.setCommentText(commentText);
		postComment.setDateWhen(now);
		postComment.setUser(loggedUser);
		
		Post post = postRepo.getById(postId);
		postComment.setPost(post);
		
		postCommentRepo.save(postComment);
		
		return postComment;
	}
	
	
	public Boolean deletePostComment(Integer id) {
		PostComment postComment = postCommentRepo.getById(id);
		postCommentRepo.delete(postComment);
		
		return true;
	}
	
	public PostLike savePostLike(Integer postId, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		Date now = new Date();
		
		PostLike postLike = new PostLike();
		
		postLike.setDateWhen(now);
		postLike.setUser(loggedUser);
		
		Post post = postRepo.findById(postId).get();
		postLike.setPost(post);
		
		PostLikePK postLikePK = new PostLikePK(loggedUser.getId(), post.getId());
		
		postLike.setId(postLikePK);
		
		postLikeRepo.save(postLike);
		
		return postLike; 
	}
	
	public Boolean deletePostLike(Integer postId, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		
		PostLike postLike = postLikeRepo.findById(new PostLikePK(loggedUser.getId(), postId)).get();
		
		postLikeRepo.delete(postLike);
			
		return true;
	}
	
}
