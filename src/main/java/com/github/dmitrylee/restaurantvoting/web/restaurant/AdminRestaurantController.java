package com.github.dmitrylee.restaurantvoting.web.restaurant;

import com.github.dmitrylee.restaurantvoting.error.IllegalRequestDataException;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.repository.RestaurantRepository;
import com.github.dmitrylee.restaurantvoting.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public final static String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;

    public AdminRestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by("name"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable Integer id) {
        log.info("get restaurant id = {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurant_menu", allEntries = true)
    public void delete(@PathVariable Integer id) {
        log.info("delete restaurant id = {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurant_menu", allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant with name = {}", restaurant.getName());
        ValidationUtil.checkNew(restaurant);
        try {
            repository.save(restaurant);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalRequestDataException(String.format("name %s already exists", restaurant.getName()));
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uri).body(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "restaurant_menu", allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id) {
        log.info("update restaurant id = {}", id);
        restaurant.setId(id);
        ValidationUtil.assureIdConsistent(restaurant, restaurant.id());
        try {
            ValidationUtil.checkNotFoundWithId(repository.save(restaurant), restaurant.id());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalRequestDataException(String.format("name %s already exists", restaurant.getName()));
        }
    }
}
