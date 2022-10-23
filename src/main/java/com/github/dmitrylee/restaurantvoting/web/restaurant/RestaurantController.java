package com.github.dmitrylee.restaurantvoting.web.restaurant;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.repository.RestaurantRepository;
import com.github.dmitrylee.restaurantvoting.to.RestaurantTo;
import com.github.dmitrylee.restaurantvoting.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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
        return repository.findAll();
    }

    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with menu");
        List<Restaurant> allWithMenu = repository.getAllWithMenu(LocalDate.now());
        return RestaurantUtil.getTos(allWithMenu);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Integer id) {
        log.info("get restaurant id = {}", id);
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("{id}/with-menu")
    public RestaurantTo getByIdWithMenu(@PathVariable Integer id) {
        log.info("get restaurant id = {} with menu", id);
        return RestaurantUtil.getTo(repository.getByIdWithMenu(id, LocalDate.now()));
    }
}
