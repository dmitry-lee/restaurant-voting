package com.github.dmitrylee.restaurantvoting.web.restaurant;

import com.github.dmitrylee.restaurantvoting.model.Restaurant;
import com.github.dmitrylee.restaurantvoting.util.RestaurantUtil;
import com.github.dmitrylee.restaurantvoting.web.AbstractControllerTest;
import com.github.dmitrylee.restaurantvoting.web.dish.DishTestData;
import com.github.dmitrylee.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.dmitrylee.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(restaurant1, restaurant2, restaurant3)));
    }

    @Test
    @WithAnonymousUser
    void getUnAuth () throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllWithMenu() throws Exception {
        restaurant2.setDishes(DishTestData.restaurant2Menu);
        restaurant3.setDishes(DishTestData.restaurant3Menu);
        List<Restaurant> restaurants = List.of(restaurant2, restaurant3);
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTos(restaurants)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT2_ID))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2));
    }

    @Test
    void getByIdWithMenu() throws Exception {
        restaurant2.setDishes(List.of(DishTestData.dish4, DishTestData.dish5, DishTestData.dish6));
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT2_ID + "/with-menu"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTo(restaurant2)));
    }
}