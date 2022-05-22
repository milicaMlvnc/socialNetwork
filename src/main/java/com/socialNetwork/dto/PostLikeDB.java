package com.socialNetwork.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialNetwork.entity.PostLike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PostLikeDB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateWhen;
	
	private PostDB fkPost;
	private UserDB fkUser;
	
	public PostLikeDB(PostLike ent) {
		this(ent, false);
	}
	
	public PostLikeDB(PostLike ent, Boolean includeFks) {
		this.dateWhen = ent.getDateWhen();
		
		if (includeFks) {
			this.fkPost = new PostDB(ent.getPost());
			this.fkUser = new UserDB(ent.getUser());
		}
	}
	
}
