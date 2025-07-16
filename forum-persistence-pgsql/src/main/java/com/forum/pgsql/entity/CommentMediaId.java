package com.forum.pgsql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@Embeddable
public class CommentMediaId implements Serializable {
    private static final long serialVersionUID = 6256215962634317996L;
    @Size(max = 24)
    @NotNull
    @Column(name = "mongo_id", nullable = false, length = 24)
    private String mongoId;

    @NotNull
    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommentMediaId entity = (CommentMediaId) o;
        return Objects.equals(this.mongoId, entity.mongoId) &&
                Objects.equals(this.mediaId, entity.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mongoId, mediaId);
    }

}