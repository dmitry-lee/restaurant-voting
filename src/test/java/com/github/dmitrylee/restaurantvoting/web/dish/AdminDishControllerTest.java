package com.github.dmitrylee.restaurantvoting.web.dish;

import com.github.dmitrylee.restaurantvoting.model.Dish;
import com.github.dmitrylee.restaurantvoting.repository.DishRepository;
import com.github.dmitrylee.restaurantvoting.to.DishTo;
import com.github.dmitrylee.restaurantvoting.util.DishUtil;
import com.github.dmitrylee.restaurantvoting.util.JsonUtil;
import com.github.dmitrylee.restaurantvoting.web.AbstractControllerTest;
import com.github.dmitrylee.restaurantvoting.web.restaurant.RestaurantTestData;
import com.github.dmitrylee.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(UserTestData.ADMIN_MAIL)
class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + "/";

    @Autowired
    private DishRepository repository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, String.valueOf(RestaurantTestData.RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_TO_MATCHER.contentJson(DishUtil.getTos(DishTestData.restaurant1Menu)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.DISH1_ID, String.valueOf(RestaurantTestData.RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_TO_MATCHER.contentJson(DishUtil.getTo(DishTestData.dish1)));
    }

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.DISH1_ID, String.valueOf(RestaurantTestData.RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.DISH1_ID, String.valueOf(RestaurantTestData.RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DishTestData.DISH1_ID, String.valueOf(RestaurantTestData.RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Dish> dishes = repository.getAll(RestaurantTestData.RESTAURANT1_ID);
        Assertions.assertEquals(dishes, List.of(DishTestData.dish2, DishTestData.dish3));
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL, String.valueOf(RestaurantTestData.RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        DishTo createdTo = DishTestData.DISH_TO_MATCHER.readFromJson(actions);
        Dish created = DishUtil.getFromTo(createdTo);
        int newId = created.id();
        newDish.setId(newId);
        DishTestData.DISH_MATCHER.assertMatch(created, newDish);
        DishTestData.DISH_MATCHER.assertMatch(repository.getById(newId), newDish);
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL, String.valueOf(RestaurantTestData.RESTAURANT1_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DishTestData.DISH_MATCHER.assertMatch(repository.getById(DishTestData.DISH2_ID), DishTestData.getUpdated());
    }
}