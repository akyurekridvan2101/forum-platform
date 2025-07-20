package com.forum.pgsql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Size(max = 30)
    @NotNull
    @Column(name = "code", nullable = false, length = 30)
    private String code;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

}