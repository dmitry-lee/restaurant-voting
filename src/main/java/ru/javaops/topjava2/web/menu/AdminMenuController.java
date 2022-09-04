package ru.javaops.topjava2.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.util.DishUtil;
import ru.javaops.topjava2.util.MenuUtil;
import ru.javaops.topjava2.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public final static String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final MenuRepository repository;

    public AdminMenuController(MenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<MenuTo> getAll(@PathVariable Integer restaurantId) {
        log.info("get menu for restaurant id = {}", restaurantId);
        return MenuUtil.getTos(repository.getAll(restaurantId));
    }

    @GetMapping("/{id}")
    public MenuTo get(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        log.info("get menu id = {} for restaurant id = {}", id, restaurantId);
        return MenuUtil.getTo(repository.get(restaurantId, id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        log.info("delete menu id = {} for restaurant id = {}", id, restaurantId);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTo> createWithLocation(@Valid @RequestBody MenuTo menuTo, @PathVariable Integer restaurantId) {
        log.info("create menu for restaurant id = {}", restaurantId);
        Menu created = new Menu(menuTo.getDate(), new Restaurant(restaurantId), DishUtil.getFromTos(menuTo.getDishes()));
        ValidationUtil.checkNew(created);
        repository.save(created);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(restaurantId, created.id()).toUri();
        return ResponseEntity.created(uri).body(MenuUtil.getTo(created));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable Integer restaurantId) {
        log.info("update menu id = {} for restaurant id = {}", menuTo.id(), restaurantId);
        ValidationUtil.assureIdConsistent(menuTo, menuTo.id());
        Menu updated = MenuUtil.getFromTo(menuTo);
        updated.setRestaurant(new Restaurant(restaurantId));
        repository.save(updated);
    }
}
