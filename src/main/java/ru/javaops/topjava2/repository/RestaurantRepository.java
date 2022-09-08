package ru.javaops.topjava2.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@CacheConfig(cacheNames = "restaurant_menu")
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Cacheable
    @Query("select distinct r from Restaurant r join fetch r.dishes d where d.menuDate=?1")
    List<Restaurant> getAllWithMenu(LocalDate date);

    @Cacheable
    @Query("select distinct r from Restaurant r join fetch r.dishes d where r.id=?1 and d.menuDate=?2")
    Restaurant getByIdWithMenu(int restaurantId, LocalDate date);
}