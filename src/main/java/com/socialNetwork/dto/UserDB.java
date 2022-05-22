package com.socialNetwork.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialNetwork.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String mail;
	private String name;
	private String pass;
	private String username;
	
//	private List<Friendship> friendships1;
//
//	private List<Friendship> friendships2;
//
//	private List<Post> posts;
//
//	private List<PostComment> postComments;
//
//	private List<PostLike> postLikes;
//
//	private List<UserMessage> userMessagesSender;
//
//	private List<UserMessage> userMessagesReceiver;
	
	public UserDB(User ent) {
		this.id = ent.getId();
		this.mail = ent.getMail();
		this.name = ent.getName();
		this.pass = ent.getPass();
		this.username = ent.getUsername();
	}

}
