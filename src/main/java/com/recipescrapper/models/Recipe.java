package com.recipescrapper.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="recipes")
public class Recipe {

    @Id
    private String id;
    private String name;
    private Integer duration;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;
}
