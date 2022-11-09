package com.github.dmitrylee.restaurantvoting.repository;

import com.github.dmitrylee.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 AND d.menuDate=?2 ORDER BY d.name")
    List<Dish> getAll(int restaurantId, LocalDate date);

    @Query("select d from Dish d where d.restaurant.id=?1 and d.id=?2")
    Optional<Dish> getById(int restaurantId, int id);
}
