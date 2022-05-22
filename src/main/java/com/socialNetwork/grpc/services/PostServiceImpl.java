package com.socialNetwork.grpc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.socialNetwork.controller.PostCommentController;
import com.socialNetwork.controller.PostLikeController;
import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;
import com.socialNetwork.utils.Utils;

import grpcPost.NoParamRequest;
import grpcPost.PostList;
import grpcPost.PostProto;
import grpcPost.PostServiceGrpc.PostServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PostServiceImpl extends PostServiceImplBase {

	@Autowired private PostRepository postRepo;
	@Autowired private UserService userService;
	@Autowired private PostCommentController postComContr;
	@Autowired private PostLikeController postLikeC;
	
	@Override
	public void getPostsForUserFriends(PostProto post, StreamObserver<PostList> res) {
		User loggedUser = userService.buildUser(post.getUsern());
		
		List<Post> posts = postRepo.findFriendsPosts(loggedUser.getId());
		
		List<PostProto> list = new ArrayList<PostProto>();
		
		for (Post p: posts) {
			PostProto onePost = PostProto.newBuilder().setId(p.getId())
					.setPostText(p.getPostText())
					.setTitle(p.getTitle())
					.setUsern(p.getUser().getUsername())
					.build();
			list.add(onePost);
		}
		
		PostList response = PostList.newBuilder().addAllPosts(list).build();
		
		res.onNext(response);
		res.onCompleted();
	}
	
	@Override
	@Transactional
	public void getAll(NoParamRequest request, StreamObserver<PostList> res) {
		List<Post> posts = postRepo.findAll();
		List<PostProto> postsResponse = new ArrayList<>();
		
		for (Post post : posts) {
			PostProto postProto = PostProto.newBuilder().setId(post.getId())
					.setPostText(post.getPostText())
					.setTitle(post.getTitle())
					.setUsern(post.getUser().getUsername())
					.build();
			postsResponse.add(postProto);
		}
		PostList response = PostList.newBuilder().addAllPosts(postsResponse).build();
		
		res.onNext(response);
		res.onCompleted();
	}
	
	@Override
	public void savePost(PostProto post, StreamObserver<PostProto> res) {
		Post postToSave = new Post();
		
		boolean isUpdate = post.getId() != -1;
        if (isUpdate) {
        	postToSave = postRepo.getById(post.getId());
        }
        
        Date now = new Date();
        
        postToSave.setDatePosted(now);
        postToSave.setPostText(post.getPostText());
        postToSave.setTitle(post.getTitle());
        
        User loggedUser = userService.buildUser(post.getUsern());
        postToSave.setUser(loggedUser);
        
        postToSave = postRepo.saveAndFlush(postToSave);
		
		PostProto response = PostProto.newBuilder().setId(postToSave.getId())
				.setPostText(postToSave.getPostText())
				.setTitle(postToSave.getTitle())
				.setUsern(postToSave.getUser().getUsername())
				.build();
		
		res.onNext(response);
		res.onCompleted();
	}
	
	@Override
	@Transactional
	public void deletePost(PostProto post, StreamObserver<PostProto> res) {
		Post postToDelete = postRepo.getById(post.getId());
		
		if (!Utils.isEmpty(postToDelete.getPostComments()) && postToDelete.getPostComments() != null) {
			for (PostComment postComm : postToDelete.getPostComments()) {
				postComContr.delete(postComm.getId()); 
			}
		}
		
		if (!Utils.isEmpty(postToDelete.getPostLikes()) && postToDelete.getPostLikes() != null) {
			for (PostLike postLike : postToDelete.getPostLikes()) {
				postLikeC.delete(postLike.getPost().getId(), postLike.getUser().getId());
			}
		}
		
		postRepo.delete(postToDelete);
		
		PostProto response = PostProto.newBuilder()
				.setPostText("Post with id: " + post.getId() + "deleted!")
				.build();
		
		res.onNext(response);
		res.onCompleted();
		
	}
	
	@Override
	@Transactional
	public void getOne(PostProto post, StreamObserver<PostProto> res) {
		Post postFetched = postRepo.getById(post.getId());
		
		PostProto response = PostProto.newBuilder().setId(postFetched.getId())
				.setPostText(postFetched.getPostText())
				.setTitle(postFetched.getTitle())
				.setUsern(postFetched.getUser().getUsername())
				.build();
		
		res.onNext(response);
		res.onCompleted();
	}
	
}
