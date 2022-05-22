package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the friendship database table.
 * 
 */
@Entity
@NamedQuery(name="Friendship.findAll", query="SELECT f FROM Friendship f")
public class Friendship implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FriendshipPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_when")
	private Date dateWhen;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user1_id", insertable = false, updatable=false)
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user2_id", insertable = false, updatable=false)
	private User user2;

	public Friendship() {
	}

	public FriendshipPK getId() {
		return this.id;
	}

	public void setId(FriendshipPK id) {
		this.id = id;
	}

	public Date getDateWhen() {
		return this.dateWhen;
	}

	public void setDateWhen(Date dateWhen) {
		this.dateWhen = dateWhen;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}