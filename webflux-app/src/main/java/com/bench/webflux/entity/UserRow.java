package com.bench.webflux.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("users")
public class UserRow {
    @Id
    private Long id;
    private String email;
    private String fullName;
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String f) {
        this.fullName = f;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant c) {
        this.createdAt = c;
    }
}
