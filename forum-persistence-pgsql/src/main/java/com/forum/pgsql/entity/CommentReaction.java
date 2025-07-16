package com.forum.pgsql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "comment_reactions")
public class CommentReaction {
    @EmbeddedId
    private CommentReactionId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "reaction", nullable = false)
    private Short reaction;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reacted_at")
    private Instant reactedAt;

}