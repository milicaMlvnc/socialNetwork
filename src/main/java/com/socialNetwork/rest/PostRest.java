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

import com.socialNetwork.controller.PostController;
import com.socialNetwork.dto.PostDB;

@RestController
@RequestMapping("/post")
public class PostRest {
 
	@Autowired private PostController postController;
	 
	@GetMapping(value = "/getPostsForUserFriends")
    public List<PostDB> getPostsForUserFriends(@RequestHeader String username) {
        return postController.getPostsForUserFriends(username);
    }
	
	@GetMapping(value = "/getAll")
    public List<PostDB> getAll() {
        return postController.getAll();
    }
	
	@PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
	public PostDB save(@RequestBody PostDB postDB, @RequestHeader String username) {
		return postController.save(postDB, username);
	}
	
	@GetMapping(value = "/delete")
	public void delete(@RequestParam(name = "id") Integer id) {
		postController.delete(id);
	}
	
	@GetMapping(value = "/getOne")
    public PostDB getOne(@RequestParam Integer id) {
        return postController.getOne(id);
    }
	
}
