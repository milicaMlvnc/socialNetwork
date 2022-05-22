package com.socialNetwork.grpc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.socialNetwork.entity.Post;
import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.PostLikePK;
import com.socialNetwork.entity.User;
import com.socialNetwork.repository.PostLikeRepository;
import com.socialNetwork.repository.PostRepository;
import com.socialNetwork.service.UserService;

import grpcPost.NoParamRequest;
import grpcPost.PostLikeProto;
import grpcPost.PostLikeServiceGrpc.PostLikeServiceImplBase;
import grpcPost.PostLikeToDelete;
import grpcPost.PostProto;
import grpcPost.UserProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PostLikeServiceImpl extends PostLikeServiceImplBase {
	
	@Autowired private PostLikeRepository postLikeRepo;
	@Autowired private PostRepository postRepo;
	@Autowired private UserService userService;
	
	@Override
	@Transactional
	public void save(PostLikeProto request, StreamObserver<PostLikeProto> response) {
		User loggedUser = userService.buildUser(request.getUser().getUsername());
		Date now = new Date();
		
		PostLike postLike = new PostLike();
		postLike.setDateWhen(now);
		postLike.setUser(loggedUser);
		
		Post post = postRepo.getById(request.getPost().getId());
		postLike.setPost(post);
		
		PostLikePK postLikePK = new PostLikePK(loggedUser.getId(), post.getId());
		postLike.setId(postLikePK);
		
		postLikeRepo.save(postLike);
		
		PostLikeProto res = PostLikeProto.newBuilder().setDate(now.toString())
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
	public void delete(PostLikeToDelete request, StreamObserver<NoParamRequest> response) {
		PostLike postLike = postLikeRepo.getById(new PostLikePK(request.getUserId(), request.getPostId()));
	
		postLikeRepo.delete(postLike);
		
		NoParamRequest res = NoParamRequest.newBuilder().build();
		
		response.onNext(res);
		response.onCompleted();
	}
}
