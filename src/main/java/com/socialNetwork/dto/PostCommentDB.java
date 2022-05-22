package com.socialNetwork.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialNetwork.entity.PostComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PostCommentDB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String commentText;
	private Date dateWhen;
	
	private PostDB fkPost;
	private UserDB fkUser;
	
	public PostCommentDB(PostComment ent) {
		this(ent, false);
	}
	
	public PostCommentDB(PostComment ent, Boolean includeFks) {
		this.id = ent.getId();
		this.commentText = ent.getCommentText();
		this.dateWhen = ent.getDateWhen();
		
		if (includeFks) {
			this.fkPost = new PostDB(ent.getPost());
			this.fkUser = new UserDB(ent.getUser());
		}
	}
	
}
