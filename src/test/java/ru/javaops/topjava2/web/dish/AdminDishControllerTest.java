package ru.javaops.topjava2.web.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.DishUtil;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.user.UserTestData;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.dish.DishTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT1_ID;

@WithUserDetails(UserTestData.ADMIN_MAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + "/";

    @Autowired
    private DishRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getTos(restaurant1Menu)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID, String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(DishUtil.getTo(dish1)));
    }

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID, String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID, String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID, String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Dish> dishes = repository.getAll(RESTAURANT1_ID);
        Assertions.assertEquals(dishes, List.of(dish2, dish3));
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL, String.valueOf(RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        DishTo createdTo = DISH_TO_MATCHER.readFromJson(actions);
        Dish created = DishUtil.getFromTo(createdTo);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL, String.valueOf(RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(repository.getById(DISH2_ID), DishTestData.getUpdated());
    }
}