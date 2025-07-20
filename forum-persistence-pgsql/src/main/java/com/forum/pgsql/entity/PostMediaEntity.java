package com.forum.pgsql.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "post_media")
public class PostMediaEntity {
    @EmbeddedId
    private PostMediaIdEntity id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postEntity;

    @MapsId("mediaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "media_id", nullable = false)
    private MediaEntity mediaEntity;

}