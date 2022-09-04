package ru.javaops.topjava2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
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

    public final static String REST_URL = "/api/admin/restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final DishRepository repository;

    public AdminDishController(DishRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DishTo> getAll(@PathVariable Integer restaurantId, @PathVariable Integer menuId) {
        log.info("get all dishes from menu id = {} for restaurant id = {}", menuId, restaurantId);
        return DishUtil.getTos(repository.getAll(restaurantId, menuId));
    }

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable Integer restaurantId, @PathVariable Integer menuId, @PathVariable Integer id) {
        log.info("get dish id = {} from menu id = {} for restaurant id = {}", id, menuId, restaurantId);
        return DishUtil.getTo(repository.getById(restaurantId, menuId, id).orElseThrow());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer restaurantId, @PathVariable String menuId, @PathVariable Integer id) {
        log.info("delete dish id = {} from menu id = {} for restaurant id = {}", id, menuId, restaurantId);
        repository.deleteExisted(id);
    }

    @PostMapping
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody DishTo dishTo,
                                                     @PathVariable Integer restaurantId,
                                                     @PathVariable Integer menuId) {
        log.info("create dish for restaurant id = {} and menu id = {}", restaurantId, menuId);
        Dish created = DishUtil.getFromTo(dishTo);
        ValidationUtil.checkNew(created);
        created.setMenu(new Menu(menuId));
        repository.save(created);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, menuId, created.id()).toUri();
        return ResponseEntity.created(uri).body(DishUtil.getTo(created));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo,
                       @PathVariable Integer restaurantId,
                       @PathVariable Integer menuId) {
        log.info("update dish for restaurant id = {} and menu id = {}", restaurantId, menuId);
        ValidationUtil.assureIdConsistent(dishTo, dishTo.id());
        Dish updated = DishUtil.getFromTo(dishTo);
        updated.setMenu(new Menu(menuId));
        repository.save(updated);
    }
}
