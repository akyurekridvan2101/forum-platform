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
public class UserFollowerIdEntity implements Serializable {
    private static final long serialVersionUID = 6472805642367260472L;
    @NotNull
    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @NotNull
    @Column(name = "followed_id", nullable = false)
    private Long followedId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserFollowerIdEntity entity = (UserFollowerIdEntity) o;
        return Objects.equals(this.followerId, entity.followerId) &&
                Objects.equals(this.followedId, entity.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followedId);
    }

}