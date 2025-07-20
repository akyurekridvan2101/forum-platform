package com.forum.pgsql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@Embeddable
public class PostMediaIdEntity implements Serializable {
    private static final long serialVersionUID = 4093360008585925929L;
    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @NotNull
    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostMediaIdEntity entity = (PostMediaIdEntity) o;
        return Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.mediaId, entity.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, mediaId);
    }

}