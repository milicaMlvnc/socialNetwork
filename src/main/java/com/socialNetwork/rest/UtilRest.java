package com.socialNetwork.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialNetwork.controller.UtilController;

@RestController
@RequestMapping("/util")
public class UtilRest {

	@Autowired private UtilController utilController;
	
	@GetMapping(value = "/fillDatabase")
    public Boolean fillDatabase() {
        return utilController.fillDatabase();
    }
	
	@GetMapping(value = "/fillWithPosts")
    public Boolean fillWithPosts() {
        return utilController.fillWithPosts();
    }
	
}
