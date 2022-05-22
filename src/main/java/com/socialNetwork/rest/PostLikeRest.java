package com.socialNetwork.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialNetwork.controller.PostLikeController;
import com.socialNetwork.dto.PostLikeDB;

@RestController
@RequestMapping("/postLike")
public class PostLikeRest {

    @Autowired private PostLikeController postLikeController;
	
	@PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
	public PostLikeDB save(@RequestBody PostLikeDB postLikeDB, @RequestHeader String username) {
		return postLikeController.save(postLikeDB, username);
	}
	
	@GetMapping(value = "/delete")
	public void delete(@RequestParam Integer userId, @RequestParam Integer postId) {
		postLikeController.delete(userId, postId);
	}
	
}
