package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo getTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName(), DishUtil.getTos(restaurant.getDishes()));
    }

    public static List<RestaurantTo> getTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::getTo).collect(Collectors.toList());
    }

    public static Restaurant getFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), DishUtil.getFromTos(restaurantTo.getDishes()));
    }

    public static List<Restaurant> getFromTos(List<RestaurantTo> restaurantTos) {
        return restaurantTos.stream().map(RestaurantUtil::getFromTo).collect(Collectors.toList());
    }
}
