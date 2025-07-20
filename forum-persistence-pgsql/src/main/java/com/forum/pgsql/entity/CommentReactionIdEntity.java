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
public class CommentReactionIdEntity implements Serializable {
    private static final long serialVersionUID = 3116389979997621451L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Size(max = 24)
    @NotNull
    @Column(name = "mongo_id", nullable = false, length = 24)
    private String mongoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommentReactionIdEntity entity = (CommentReactionIdEntity) o;
        return Objects.equals(this.mongoId, entity.mongoId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mongoId, userId);
    }

}