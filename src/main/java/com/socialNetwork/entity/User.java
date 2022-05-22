package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String mail;

	private String name;

	private String pass;

	private String username;

	//bi-directional many-to-one association to Friendship
	@OneToMany(mappedBy="user1")
	private List<Friendship> friendships1;

	//bi-directional many-to-one association to Friendship
	@OneToMany(mappedBy="user2")
	private List<Friendship> friendships2;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="user")
	private List<Post> posts;

	//bi-directional many-to-one association to PostComment
	@OneToMany(mappedBy="user")
	private List<PostComment> postComments;

	//bi-directional many-to-one association to PostLike
	@OneToMany(mappedBy="user")
	private List<PostLike> postLikes;

	//bi-directional many-to-one association to UserMessage
	@OneToMany(mappedBy="userSender")
	private List<UserMessage> userMessagesSender;

	//bi-directional many-to-one association to UserMessage
	@OneToMany(mappedBy="userReceiver")
	private List<UserMessage> userMessagesReceiver;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Friendship> getFriendships1() {
		return this.friendships1;
	}

	public void setFriendships1(List<Friendship> friendships1) {
		this.friendships1 = friendships1;
	}

	public Friendship addFriendships1(Friendship friendships1) {
		getFriendships1().add(friendships1);
		friendships1.setUser1(this);

		return friendships1;
	}

	public Friendship removeFriendships1(Friendship friendships1) {
		getFriendships1().remove(friendships1);
		friendships1.setUser1(null);

		return friendships1;
	}

	public List<Friendship> getFriendships2() {
		return this.friendships2;
	}

	public void setFriendships2(List<Friendship> friendships2) {
		this.friendships2 = friendships2;
	}

	public Friendship addFriendships2(Friendship friendships2) {
		getFriendships2().add(friendships2);
		friendships2.setUser2(this);

		return friendships2;
	}

	public Friendship removeFriendships2(Friendship friendships2) {
		getFriendships2().remove(friendships2);
		friendships2.setUser2(null);

		return friendships2;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setUser(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setUser(null);

		return post;
	}

	public List<PostComment> getPostComments() {
		return this.postComments;
	}

	public void setPostComments(List<PostComment> postComments) {
		this.postComments = postComments;
	}

	public PostComment addPostComment(PostComment postComment) {
		getPostComments().add(postComment);
		postComment.setUser(this);

		return postComment;
	}

	public PostComment removePostComment(PostComment postComment) {
		getPostComments().remove(postComment);
		postComment.setUser(null);

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
		postLike.setUser(this);

		return postLike;
	}

	public PostLike removePostLike(PostLike postLike) {
		getPostLikes().remove(postLike);
		postLike.setUser(null);

		return postLike;
	}

	public List<UserMessage> getUserMessagesSender() {
		return this.userMessagesSender;
	}

	public void setUserMessagesSender(List<UserMessage> userMessagesSender) {
		this.userMessagesSender = userMessagesSender;
	}

	public UserMessage addUserMessagesSender(UserMessage userMessagesSender) {
		getUserMessagesSender().add(userMessagesSender);
		userMessagesSender.setUserSender(this);

		return userMessagesSender;
	}

	public UserMessage removeUserMessagesSender(UserMessage userMessagesSender) {
		getUserMessagesSender().remove(userMessagesSender);
		userMessagesSender.setUserSender(null);

		return userMessagesSender;
	}

	public List<UserMessage> getUserMessagesReceiver() {
		return this.userMessagesReceiver;
	}

	public void setUserMessagesReceiver(List<UserMessage> userMessagesReceiver) {
		this.userMessagesReceiver = userMessagesReceiver;
	}

	public UserMessage addUserMessagesReceiver(UserMessage userMessagesReceiver) {
		getUserMessagesReceiver().add(userMessagesReceiver);
		userMessagesReceiver.setUserReceiver(this);

		return userMessagesReceiver;
	}

	public UserMessage removeUserMessagesReceiver(UserMessage userMessagesReceiver) {
		getUserMessagesReceiver().remove(userMessagesReceiver);
		userMessagesReceiver.setUserReceiver(null);

		return userMessagesReceiver;
	}

}