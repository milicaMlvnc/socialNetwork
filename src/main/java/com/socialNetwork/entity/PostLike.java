package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the post_like database table.
 * 
 */
@Entity
@Table(name="post_like")
@NamedQuery(name="PostLike.findAll", query="SELECT p FROM PostLike p")
public class PostLike implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PostLikePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_when")
	private Date dateWhen;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="post_id", insertable = false, updatable=false)
	private Post post;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", insertable = false, updatable=false)
	private User user;

	public PostLike() {
	}

	public PostLikePK getId() {
		return this.id;
	}

	public void setId(PostLikePK id) {
		this.id = id;
	}

	public Date getDateWhen() {
		return this.dateWhen;
	}

	public void setDateWhen(Date dateWhen) {
		this.dateWhen = dateWhen;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}