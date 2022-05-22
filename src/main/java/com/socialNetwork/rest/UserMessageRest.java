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

import com.socialNetwork.controller.UserMessageController;
import com.socialNetwork.dto.UserMessageDB;

@RestController
@RequestMapping("/userMessage")
public class UserMessageRest {

	@Autowired private UserMessageController userMessageController;
	
	@PostMapping(value = "/send", consumes = "application/json", produces = "application/json")
	public UserMessageDB send(@RequestBody UserMessageDB userMessageDB, @RequestHeader String username) {
		return userMessageController.send(userMessageDB, username);
	}
	
	@GetMapping(value = "/getConversation")
    public List<UserMessageDB> getConversation(@RequestParam Integer userId, @RequestHeader String username) {
        return userMessageController.getConversation(userId, username);
    }
	
}