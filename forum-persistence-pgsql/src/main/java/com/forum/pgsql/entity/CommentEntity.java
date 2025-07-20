package com.forum.pgsql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @ColumnDefault("nextval('comments_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 24)
    @NotNull
    @Column(name = "mongo_id", nullable = false, length = 24)
    private String mongoId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentCommentEntity;

    @ColumnDefault("false")
    @Column(name = "is_hidden")
    private Boolean isHidden;

    @ColumnDefault("0")
    @Column(name = "like_count")
    private Integer likeCount;

    @ColumnDefault("0")
    @Column(name = "reply_count")
    private Integer replyCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}