package com.recipescrapper.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="directions")
public class Direction {
    @Id @Column(name="id")
    private String id;
    @Column(name="description")
    private String description;
    @Column(name="order")
    private Integer order;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Recipe.class)
    private Recipe recipe;

    public Direction(String description, Integer order) {
        this.description = description;
        this.order = order;
    }
}
