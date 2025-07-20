package com.forum.pgsql.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "post_tags")
public class PostTagEntity {
    @EmbeddedId
    private PostTagIdEntity id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tagEntity;

}