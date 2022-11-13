package com.github.dmitrylee.restaurantvoting.web.restaurant;

import com.github.dmitrylee.restaurantvoting.error.NotFoundException;
import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.repository.RestaurantRepository;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import com.github.dmitrylee.restaurantvoting.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    public final static String REST_URL = "/api/restaurants";

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.findAll(Sort.by("name"));
    }

    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with menu");
        List<Restaurant> allWithMenu = repository.getAllWithMenu(LocalDate.now());
        return RestaurantUtil.getTos(allWithMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant id = {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("{id}/with-menu")
    public ResponseEntity<RestaurantTo> getByIdWithMenu(@PathVariable int id) {
        log.info("get restaurant id = {} with menu", id);
        Optional<Restaurant> optionalRestaurant = repository.getByIdWithMenu(id, LocalDate.now());
        if (optionalRestaurant.isPresent()) {
            return ResponseEntity.ok(RestaurantUtil.getTo(optionalRestaurant.get()));
        }
        throw new NotFoundException("Restaurant doesn't exist or has no actual menu");
    }
}
