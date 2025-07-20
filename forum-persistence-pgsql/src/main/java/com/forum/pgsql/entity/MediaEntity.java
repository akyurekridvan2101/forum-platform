package com.forum.pgsql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "media")
public class MediaEntity {
    @Id
    @ColumnDefault("nextval('media_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false, length = Integer.MAX_VALUE)
    private String url;

    @Size(max = 30)
    @NotNull
    @Column(name = "media_type", nullable = false, length = 30)
    private String mediaType;

    @Column(name = "size_in_bytes")
    private Long sizeInBytes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private UserEntity uploadedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "uploaded_at")
    private Instant uploadedAt;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

}