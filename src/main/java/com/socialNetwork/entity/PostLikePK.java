package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the post_like database table.
 * 
 */
@Embeddable
public class PostLikePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private Integer userId;

	@Column(name="post_id", insertable=false, updatable=false)
	private Integer postId;

	public PostLikePK() {
		
	}
	
	public PostLikePK(Integer userId, Integer postId) {
		this.userId = userId;
		this.postId = postId;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostLikePK)) {
			return false;
		}
		PostLikePK castOther = (PostLikePK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.postId == castOther.postId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.postId;
		
		return hash;
	}
}