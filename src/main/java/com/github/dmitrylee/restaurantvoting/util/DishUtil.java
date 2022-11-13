package com.github.dmitrylee.restaurantvoting.util;

import com.github.dmitrylee.restaurantvoting.model.Dish;
import com.github.dmitrylee.restaurantvoting.to.DishTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static DishTo getTo(Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> getTos(List<Dish> dishes) {
        return dishes.stream().map(DishUtil::getTo).collect(Collectors.toList());
    }

    public static Dish getFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }
}
