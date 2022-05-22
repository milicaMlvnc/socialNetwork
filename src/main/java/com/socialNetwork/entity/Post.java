package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_posted")
	private Date datePosted;

	@Column(name="post_text")
	private String postText;

	private String title;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to PostComment
	@OneToMany(mappedBy="post")
	private List<PostComment> postComments;

	//bi-directional many-to-one association to PostLike
	@OneToMany(mappedBy="post")
	private List<PostLike> postLikes;

	public Post() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatePosted() {
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getPostText() {
		return this.postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PostComment> getPostComments() {
		return this.postComments;
	}

	public void setPostComments(List<PostComment> postComments) {
		this.postComments = postComments;
	}

	public PostComment addPostComment(PostComment postComment) {
		getPostComments().add(postComment);
		postComment.setPost(this);

		return postComment;
	}

	public PostComment removePostComment(PostComment postComment) {
		getPostComments().remove(postComment);
		postComment.setPost(null);

		return postComment;
	}

	public List<PostLike> getPostLikes() {
		return this.postLikes;
	}

	public void setPostLikes(List<PostLike> postLikes) {
		this.postLikes = postLikes;
	}

	public PostLike addPostLike(PostLike postLike) {
		getPostLikes().add(postLike);
		postLike.setPost(this);

		return postLike;
	}

	public PostLike removePostLike(PostLike postLike) {
		getPostLikes().remove(postLike);
		postLike.setPost(null);

		return postLike;
	}

}