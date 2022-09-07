package ru.javaops.topjava2.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.user.UserTestData;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;

@WithUserDetails(UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantController.REST_URL))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(restaurant1, restaurant2, restaurant3)));
    }

    @Test
    void getAllWithMenu() {
    }

    @Test
    void get() {
    }

    @Test
    void getByIdWithMenu() {
    }
}