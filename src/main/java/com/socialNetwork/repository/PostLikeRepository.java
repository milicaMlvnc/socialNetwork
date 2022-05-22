package com.socialNetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialNetwork.entity.PostLike;
import com.socialNetwork.entity.PostLikePK;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikePK> {

}
