package com.forum.pgsql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @ColumnDefault("nextval('posts_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @ColumnDefault("false")
    @Column(name = "is_locked")
    private Boolean isLocked;

    @ColumnDefault("false")
    @Column(name = "is_pinned")
    private Boolean isPinned;

    @ColumnDefault("false")
    @Column(name = "is_hidden")
    private Boolean isHidden;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Integer viewCount;

    @ColumnDefault("0")
    @Column(name = "reply_count")
    private Integer replyCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "last_activity_at")
    private Instant lastActivityAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}