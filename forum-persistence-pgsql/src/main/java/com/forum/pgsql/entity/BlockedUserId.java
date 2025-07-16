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
public class BlockedUserId implements Serializable {
    private static final long serialVersionUID = 3920387194056714596L;
    @NotNull
    @Column(name = "blocker_id", nullable = false)
    private Long blockerId;

    @NotNull
    @Column(name = "blocked_id", nullable = false)
    private Long blockedId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BlockedUserId entity = (BlockedUserId) o;
        return Objects.equals(this.blockerId, entity.blockerId) &&
                Objects.equals(this.blockedId, entity.blockedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockerId, blockedId);
    }

}