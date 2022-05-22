package com.socialNetwork.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialNetwork.controller.PostCommentController;
import com.socialNetwork.dto.PostCommentDB;

@RestController
@RequestMapping("/postComment")
public class PostCommentRest {

	@Autowired private PostCommentController postCommentController;
	
	@PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
	public PostCommentDB save(@RequestBody PostCommentDB postCommentDB, @RequestHeader String username) {
		return postCommentController.save(postCommentDB, username);
	}
	
	@GetMapping(value = "/delete")
	public void delete(@RequestParam Integer id) {
		postCommentController.delete(id);
	}
	
	@GetMapping(value = "/allCommentsForPost")
	public List<PostCommentDB> allCommentsForPost(@RequestParam Integer postId) {
		return postCommentController.allCommentsForPost(postId);
	}
	
}
