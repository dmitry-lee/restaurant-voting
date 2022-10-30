package com.github.dmitrylee.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "dish", indexes = {@Index(name = "dish_unique_idx", columnList = "restaurant_id, menu_date, name", unique = true)})
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Positive
    private int price;

    @NotNull
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default now()")
    private LocalDate menuDate = LocalDate.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
