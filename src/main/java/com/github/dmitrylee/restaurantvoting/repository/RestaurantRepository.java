package com.github.dmitrylee.restaurantvoting.repository;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("select distinct r from Restaurant r join fetch r.dishes d where d.menuDate=?1 order by r.name, d.name")
    List<Restaurant> getAllWithMenu(LocalDate date);

    @Query("select distinct r from Restaurant r join fetch r.dishes d where r.id=?1 and d.menuDate=?2 order by d.name")
    Optional<Restaurant> getByIdWithMenu(int restaurantId, LocalDate date);
}