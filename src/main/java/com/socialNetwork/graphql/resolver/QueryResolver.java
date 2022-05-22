package com.socialNetwork.graphql.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.User;
import com.socialNetwork.entity.UserMessage;
import com.socialNetwork.repository.PostCommentRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.repository.UserMessageRepository;
import com.socialNetwork.service.UserService;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;


@Component
public class QueryResolver implements GraphQLQueryResolver {

	@Autowired private PostRepository postRepo;
	@Autowired UserMessageRepository userMessageRepo;
	@Autowired PostCommentRepository postCommentRepo;
	
	@Autowired private UserService userService;
	
	public List<Post> getPostsForUserFriends(DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		
		List<Post> posts = postRepo.findFriendsPosts(loggedUser.getId());
		
		return posts;
	}
	
	public List<Post> getAllPosts() {
    	List<Post> posts = postRepo.findAll();
    	return posts;
    }
	
    public Post getOnePost(Integer id) {
    	Post post = postRepo.findById(id).get();
    	
        return post;
    }
    
    @Transactional
    public List<PostComment> allCommentsForPost(Integer postId) {
//    	Post post = postRepo.findById(postId).get();
    	
    	
    	List<PostComment> postComments = postCommentRepo.getCommentsForPost(postId);
    	return postComments;
    }
	
    public List<UserMessage> getConversation(Integer userId, DataFetchingEnvironment env) {
		GraphQLContext context =  env.getContext();
        HttpServletRequest request = context.getHttpServletRequest().get();
        String username = request.getHeader("username");
		
		User loggedUser = userService.buildUser(username);
		
		List<UserMessage> conversation = userMessageRepo.findBySenderAndReceiver(loggedUser.getId(), userId);
		return conversation;
	}
    
}
