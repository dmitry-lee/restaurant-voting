package com.github.dmitrylee.restaurantvoting.web.dish;

import com.github.dmitrylee.restaurantvoting.error.IllegalRequestDataException;
import com.github.dmitrylee.restaurantvoting.error.NotFoundException;
import com.github.dmitrylee.restaurantvoting.model.Dish;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.repository.DishRepository;
import com.github.dmitrylee.restaurantvoting.to.DishTo;
import com.github.dmitrylee.restaurantvoting.util.DishUtil;
import com.github.dmitrylee.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "dishes")
public class AdminDishController {

    public final static String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishRepository repository;

    public AdminDishController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Cacheable(key = "'getAll:' + #restaurantId + ':' + #date", unless = "#result.empty")
    public List<DishTo> getAll(@PathVariable int restaurantId,
                               @RequestParam Optional<LocalDate> date) {
        log.info("get all dishes from for restaurant id = {}", restaurantId);
        return DishUtil.getTos(repository.getAll(restaurantId, date.orElseGet(LocalDate::now)));
    }

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish id = {} for restaurant id = {}", id, restaurantId);
        return DishUtil.getTo(repository.getById(restaurantId, id)
                .orElseThrow(() -> new NotFoundException("dish is not found")));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = {"restaurants", "dishes"}, allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish id = {} from for restaurant id = {}", id, restaurantId);
        get(restaurantId, id);
        repository.deleteExisted(id);
    }

    @PostMapping
    @CacheEvict(cacheNames = {"restaurants", "dishes"}, allEntries = true)
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody DishTo dishTo,
                                                     @PathVariable int restaurantId,
                                                     @RequestParam Optional<LocalDate> date) {
        log.info("create dish for restaurant id = {}", restaurantId);
        Dish created = DishUtil.getFromTo(dishTo);
        created.setRestaurant(new Restaurant(restaurantId));
        created.setMenuDate(date.orElseGet(LocalDate::now));
        ValidationUtil.checkNew(created);
        tryToSave(created);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id()).toUri();
        return ResponseEntity.created(uri).body(DishUtil.getTo(created));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(cacheNames = {"restaurants", "dishes"}, allEntries = true)
    public void update(@Valid @RequestBody DishTo dishTo,
                       @PathVariable int restaurantId,
                       @PathVariable int id,
                       @RequestParam Optional<LocalDate> date) {
        log.info("update dish for restaurant id = {}", restaurantId);
        repository.getById(restaurantId, id).orElseThrow();
        ValidationUtil.assureIdConsistent(dishTo, id);
        Dish updated = DishUtil.getFromTo(dishTo);
        updated.setRestaurant(new Restaurant(restaurantId));
        updated.setMenuDate(date.orElseGet(LocalDate::now));
        tryToSave(updated);
    }

    private void tryToSave(Dish dish) {
        try {
            repository.save(dish);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalRequestDataException(String.format("Dish name [%s] already exists", dish.getName()));
        }
    }
}
