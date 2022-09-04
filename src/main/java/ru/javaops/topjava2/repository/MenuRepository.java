package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ru.javaops.topjava2.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends BaseRepository<Menu> {

    @Query("select m from Menu m where m.restaurant.id=?1")
    @EntityGraph(attributePaths = {"dishes"})
    List<Menu> getAll(int restaurantId);

    @Query("select m from Menu m where m.restaurant.id=?1 and m.id=?2")
    Optional<Menu> get(int restaurantId, int id);

    @Query("select m from Menu m where m.date=?1")
    List<Menu> getByDate(LocalDate date);
}
