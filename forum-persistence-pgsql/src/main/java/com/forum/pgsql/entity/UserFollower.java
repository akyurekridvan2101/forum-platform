package com.forum.pgsql.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "user_followers")
public class UserFollower {
    @EmbeddedId
    private UserFollowerId id;

    @MapsId("followerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @MapsId("followedId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "followed_at")
    private Instant followedAt;

}