package com.socialNetwork.grpc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostComment;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostCommentRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;

import grpcPost.AllCommentsForPost;
import grpcPost.NoParamRequest;
import grpcPost.PostCommentDelete;
import grpcPost.PostCommentProto;
import grpcPost.PostCommentServiceGrpc.PostCommentServiceImplBase;
import grpcPost.PostId;
import grpcPost.PostProto;
import grpcPost.UserProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PostCommentServiceImpl extends PostCommentServiceImplBase {
	
	@Autowired private UserService userService;
	@Autowired private PostCommentRepository postCommentRepo;
	@Autowired private PostRepository postRepo;

	@Override
	@Transactional
	public void save(PostCommentProto request, StreamObserver<PostCommentProto> response) {
		User loggedUser = userService.buildUser(request.getUser().getUsername());
		Date now = new Date();
		
		PostComment comment = new PostComment();
		
		boolean isUpdate = Integer.valueOf(request.getId()) != null && request.getId() != -1;
        if (isUpdate) {
        	comment = postCommentRepo.getById(request.getId());
        }
        
        comment.setCommentText(request.getCommentText());
		comment.setDateWhen(now);
		comment.setUser(loggedUser);
		
		Post post = postRepo.getById(request.getPost().getId());
		comment.setPost(post);
		
		postCommentRepo.save(comment);
		
		PostCommentProto res = PostCommentProto.newBuilder()
				.setId(comment.getId())
				.setCommentText(comment.getCommentText())
				.setDate(now.toString())
				.setPost(PostProto.newBuilder()
						.setId(post.getId())
						.setPostText(post.getPostText())
						.setTitle(post.getTitle())
						.setUsern(post.getUser().getUsername())
						.build())
				.setUser(UserProto.newBuilder()
						.setId(loggedUser.getId())
						.setMail(loggedUser.getMail())
						.setName(loggedUser.getName())
						.setPass(loggedUser.getPass())
						.setUsername(loggedUser.getUsername())
						.build())
				.build();
		
		response.onNext(res);
		response.onCompleted();
	}
	
	@Override
	public void delete(PostCommentDelete request, StreamObserver<NoParamRequest> response) {
		PostComment postComment = postCommentRepo.getById(request.getId());
		
		postCommentRepo.delete(postComment);
		
		NoParamRequest res = NoParamRequest.newBuilder().build();
		
		response.onNext(res);
		response.onCompleted();
	}
	
	@Override
	@Transactional
	public void allCommentsForPost(PostId request, StreamObserver<AllCommentsForPost> response) {
		Post post = postRepo.getById(request.getId());
		
		List<PostComment> postComments = postCommentRepo.getCommentsForPost(request.getId());
		
		List<PostCommentProto> postCommentsProto = new ArrayList<>();
		
		for (PostComment com : postComments) {
			PostCommentProto comProto = PostCommentProto.newBuilder()
					.setId(com.getId())
					.setCommentText(com.getCommentText())
					.setDate(com.getDateWhen().toString())
					.setPost(PostProto.newBuilder()
							.setId(post.getId())
							.setPostText(post.getPostText())
							.setTitle(post.getTitle())
							.setUsern(post.getUser().getUsername())
							.build())
					.setUser(UserProto.newBuilder()
							.setId(post.getUser().getId())
							.setMail(post.getUser().getMail())
							.setName(post.getUser().getName())
							.setPass(post.getUser().getPass())
							.setUsername(post.getUser().getUsername())
							.build())
					.build();
			postCommentsProto.add(comProto);
		}
		
		AllCommentsForPost res = AllCommentsForPost.newBuilder().addAllComments(postCommentsProto).build();
		
		response.onNext(res);
		response.onCompleted();
	}
}
