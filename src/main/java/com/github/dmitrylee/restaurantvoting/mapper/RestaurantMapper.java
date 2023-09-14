package com.github.dmitrylee.restaurantvoting.mapper;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = DishMapper.class)
public interface RestaurantMapper {

    RestaurantTo restaurantToRestaurantDto(Restaurant restaurant);

    List<RestaurantTo> restaurantListToRestaurantDtoList(List<Restaurant> restaurants);
}
