package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("select distinct r from Restaurant r join fetch r.dishes d where d.menuDate=?1")
    List<Restaurant> getAllWithMenu(LocalDate date);

    @Query("select distinct r from Restaurant r join fetch r.dishes d where r.id=?1 and d.menuDate=?2")
    Restaurant getByIdWithMenu(int restaurantId, LocalDate date);
}