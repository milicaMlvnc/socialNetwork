package com.socialNetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialNetwork.entity.User;
import com.socialNetwork.repository.UserRepository;

@Service
public class UserService {

	@Autowired private UserRepository userRepo;
	
	public User buildUser(String username) {
        User u = userRepo.findUserByUsername(username).get();

        return u;
    }
	
}
