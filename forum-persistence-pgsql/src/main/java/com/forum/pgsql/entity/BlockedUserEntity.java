package com.forum.pgsql.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "blocked_users")
public class BlockedUserEntity {
    @EmbeddedId
    private BlockedUserIdEntity id;

    @MapsId("blockerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "blocker_id", nullable = false)
    private UserEntity blocker;

    @MapsId("blockedId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "blocked_id", nullable = false)
    private UserEntity blocked;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "blocked_at")
    private Instant blockedAt;

    @Column(name = "reason", length = Integer.MAX_VALUE)
    private String reason;

}