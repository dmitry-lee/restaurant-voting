package com.github.dmitrylee.restaurantvoting.mapper;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = DishMapper.class)
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantTo restaurantToRestaurantDto(Restaurant restaurant);

    List<RestaurantTo> restaurantListToRestaurantDtoList(List<Restaurant> restaurants);

}
