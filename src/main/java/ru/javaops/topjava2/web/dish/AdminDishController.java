package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.DishUtil;
import ru.javaops.topjava2.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public final static String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishRepository repository;

    public AdminDishController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DishTo> getAll(@PathVariable Integer restaurantId) {
        log.info("get all dishes from for restaurant id = {}", restaurantId);
        return DishUtil.getTos(repository.getAll(restaurantId));
    }

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        log.info("get dish id = {} for restaurant id = {}", id, restaurantId);
        return DishUtil.getTo(repository.getById(restaurantId, id).orElseThrow());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        log.info("delete dish id = {} from for restaurant id = {}", id, restaurantId);
        repository.deleteExisted(id);
    }

    @PostMapping
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody DishTo dishTo, @PathVariable Integer restaurantId) {
        log.info("create dish for restaurant id = {}", restaurantId);
        Dish created = DishUtil.getFromTo(dishTo);
        ValidationUtil.checkNew(created);
        repository.save(created);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id()).toUri();
        return ResponseEntity.created(uri).body(DishUtil.getTo(created));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable Integer restaurantId) {
        log.info("update dish for restaurant id = {}", restaurantId);
        ValidationUtil.assureIdConsistent(dishTo, dishTo.id());
        Dish updated = DishUtil.getFromTo(dishTo);
        repository.save(updated);
    }
}
