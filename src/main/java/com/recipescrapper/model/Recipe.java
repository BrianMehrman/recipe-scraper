package com.recipescrapper.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@Table(name="recipes")
public class Recipe {

    @Id
    @Column(name="id")
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="duration")
    private Integer duration;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="created_at")
    private Timestamp createdAt;
    @Column(columnDefinition = "datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP", name="updated_at")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, targetEntity = Direction.class)
    private List<Direction> directions;
}
