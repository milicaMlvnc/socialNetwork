package com.socialNetwork.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialNetwork.entity.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PostDB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date datePosted;
	private String postText;
	private String title;
	
	private UserDB fkUser;
	
	private List<PostCommentDB> postComments;
	private List<PostLikeDB> postLikes;
	
	public PostDB(Post ent) {
		this(ent, false);
	}
	
	public PostDB(Post ent, Boolean includeFks) {
		this(ent, includeFks, false);
	}
	
	public PostDB(Post ent, Boolean includeFks, Boolean includeFksList) {
		this.id = ent.getId();
		this.datePosted = ent.getDatePosted();
		this.postText = ent.getPostText();
		this.title = ent.getTitle();
		
		if (includeFks) {
			this.fkUser = new UserDB(ent.getUser());
		}
		
		if (includeFksList) {
			
		}
	}

}
