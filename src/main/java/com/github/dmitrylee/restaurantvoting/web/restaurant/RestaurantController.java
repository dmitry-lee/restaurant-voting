package com.github.dmitrylee.restaurantvoting.web.restaurant;

import com.github.dmitrylee.restaurantvoting.error.NotFoundException;
import com.github.dmitrylee.restaurantvoting.mapper.RestaurantMapper;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.repository.RestaurantRepository;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    public final static String REST_URL = "/api/restaurants";

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Cacheable(key = "'getAll'")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by("name"));
    }

    @GetMapping("/with-menu")
    @Cacheable(key = "'getAllWithMenu:' + T(java.time.LocalDate).now()")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with menu");
        List<Restaurant> allWithMenu = repository.getAllWithMenu(LocalDate.now());
        return RestaurantMapper.INSTANCE.restaurantListToRestaurantDtoList(allWithMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant id = {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("{id}/with-menu")
    @Cacheable(key = "#id + ':' + T(java.time.LocalDate).now()")
    public ResponseEntity<RestaurantTo> getByIdWithMenu(@PathVariable int id) {
        log.info("get restaurant id = {} with menu", id);
        Optional<Restaurant> optionalRestaurant = repository.getByIdWithMenu(id, LocalDate.now());
        if (optionalRestaurant.isPresent()) {
            return ResponseEntity.ok(RestaurantMapper.INSTANCE.restaurantToRestaurantDto(optionalRestaurant.get()));
        }
        throw new NotFoundException("Restaurant doesn't exist or has no actual menu");
    }
}
