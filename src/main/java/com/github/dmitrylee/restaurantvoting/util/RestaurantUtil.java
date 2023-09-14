package com.github.dmitrylee.restaurantvoting.util;

import com.github.dmitrylee.restaurantvoting.mapper.DishMapper;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo getTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName(), DishMapper.INSTANCE.dishListToDishDtoList(restaurant.getDishes()));
    }

    public static List<RestaurantTo> getTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::getTo).collect(Collectors.toList());
    }
}
