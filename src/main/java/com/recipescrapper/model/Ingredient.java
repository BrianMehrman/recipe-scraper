package com.recipescrapper.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="ingredients")
public class Ingredient {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="measurement")
    private String measurement;
    @Column(name="amount")
    private Double amount;
    @Column(name="ingredient_type")
    private String ingredientType;
    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Recipe.class)
    private Recipe recipe;

    public Ingredient(String name) {
        this.name = name;
    }
}
