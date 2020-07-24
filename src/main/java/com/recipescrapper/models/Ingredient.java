package com.recipescrapper.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="ingredients")
public class Ingredient {
    @Id
    private String id;
    private String name;
    private String measurement;
    private Double amount;
    @Column(name="ingredient_type")
    private String ingredientType;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Recipe.class)
    private Recipe recipe;
}
