package com.forum.pgsql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @ColumnDefault("nextval('tags_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "slug", nullable = false, length = 50)
    private String slug;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ColumnDefault("true")
    @Column(name = "is_approved")
    private Boolean isApproved;

    @ColumnDefault("0")
    @Column(name = "usage_count")
    private Integer usageCount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}