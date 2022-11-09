package com.github.dmitrylee.restaurantvoting.repository;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@CacheConfig(cacheNames = "restaurant_menu")
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Cacheable
    @Query("select distinct r from Restaurant r join fetch r.dishes d where d.menuDate=?1 order by r.name")
    List<Restaurant> getAllWithMenu(LocalDate date);

    @Cacheable
    @Query("select distinct r from Restaurant r join fetch r.dishes d where r.id=?1 and d.menuDate=?2 order by d.name")
    Restaurant getByIdWithMenu(int restaurantId, LocalDate date);
}