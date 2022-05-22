package com.socialNetwork.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the friendship database table.
 * 
 */
@Embeddable
public class FriendshipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user1_id", insertable=false, updatable=false)
	private int user1Id;

	@Column(name="user2_id", insertable=false, updatable=false)
	private int user2Id;

	public FriendshipPK() {
	}
	
	public FriendshipPK(Integer user1Id, Integer user2Id) {
		this.user1Id = user1Id;
		this.user2Id = user2Id;
	}
	
	public Integer getUser1Id() {
		return this.user1Id;
	}
	
	public void setUser1Id(Integer user1Id) {
		this.user1Id = user1Id;
	}
	
	public Integer getUser2Id() {
		return this.user2Id;
	}
	
	public void setUser2Id(Integer user2Id) {
		this.user2Id = user2Id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FriendshipPK)) {
			return false;
		}
		FriendshipPK castOther = (FriendshipPK)other;
		return 
			(this.user1Id == castOther.user1Id)
			&& (this.user2Id == castOther.user2Id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.user1Id;
		hash = hash * prime + this.user2Id;
		
		return hash;
	}
}