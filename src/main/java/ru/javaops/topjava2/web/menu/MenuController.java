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
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    public final static String REST_URL = "/api/menus";

    private final MenuRepository repository;

    public MenuController(MenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Menu> getTodayMenus() {
        log.info("get menus for today");
        return repository.getByDate(LocalDate.now());
    }
}
