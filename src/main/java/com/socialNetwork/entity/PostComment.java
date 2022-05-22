package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the post_comment database table.
 * 
 */
@Entity
@Table(name="post_comment")
@NamedQuery(name="PostComment.findAll", query="SELECT p FROM PostComment p")
public class PostComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="comment_text")
	private String commentText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_when")
	private Date dateWhen;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public PostComment() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
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