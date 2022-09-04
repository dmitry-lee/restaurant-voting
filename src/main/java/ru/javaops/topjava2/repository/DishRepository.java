package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import ru.javaops.topjava2.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.menu.id=?2")
    List<Dish> getAll(int restaurantId, int menuId);

    @Query("select d from Dish d where d.menu.id=?2 and d.id=?3")
    Optional<Dish> getById(int restaurantId, int menuId, int id);
}
