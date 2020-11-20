package com.recipescrapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="directions")
@NoArgsConstructor
@AllArgsConstructor
public class Direction {
    @Id @Column(name="id")
    private String id;
    @Column(name="description")
    private String description;
    @Column(name="step")
    private Integer step;
    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Recipe.class)
    private Recipe recipe;

    public Direction(String description, Integer step) {
        this.description = description;
        this.step = step;
    }

}
