package com.forum.pgsql.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "comment_media")
public class CommentMediaEntity {
    @EmbeddedId
    private CommentMediaIdEntity id;

    @MapsId("mediaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "media_id", nullable = false)
    private MediaEntity mediaEntity;

}