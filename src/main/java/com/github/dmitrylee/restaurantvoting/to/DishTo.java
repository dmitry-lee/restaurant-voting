package com.github.dmitrylee.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Positive;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo {

    @Positive
    Integer price;

    public DishTo(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
