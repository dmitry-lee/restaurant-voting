package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.to.DishTo;

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

    public static List<Dish> getFromTos(List<DishTo> dishes) {
        return dishes.stream().map(DishUtil::getFromTo).collect(Collectors.toList());
    }
}
