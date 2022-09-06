package ru.javaops.topjava2.web.menu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.util.MenuUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.user.UserTestData;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.TestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.web.menu.MenuTestData.*;

public class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuController.REST_URL + "/";

    @Autowired
    private MenuRepository repository;

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    public void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(MenuUtil.getTos(List.of(menu1))));
    }

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU1_ID, RESTAURANT1_ID, MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MENU_TO_MATCHER.contentJson(MenuUtil.getTo(menu1)));
    }

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU1_ID, RESTAURANT1_ID, MENU1_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(repository.get(RESTAURANT1_ID, MENU1_ID).isPresent());
    }

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    void deleteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU1_ID, RESTAURANT1_ID, MENU1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MenuTo created = MenuUtil.getTo(newMenu);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

    }

    @Test
    void update() {
    }
}
