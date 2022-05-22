package com.socialNetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialNetwork.entity.Friendship;
import com.socialNetwork.entity.FriendshipPK;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipPK> {

}
