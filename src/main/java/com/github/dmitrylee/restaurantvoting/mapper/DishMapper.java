package com.github.dmitrylee.restaurantvoting.mapper;

import com.github.dmitrylee.restaurantvoting.model.Dish;
import com.github.dmitrylee.restaurantvoting.to.DishTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DishMapper {

    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    DishTo dishToDishDto(Dish dish);

    List<DishTo> dishListToDishDtoList(List<Dish> dishes);

    Dish dishDtoToDish(DishTo dishTo);
}
