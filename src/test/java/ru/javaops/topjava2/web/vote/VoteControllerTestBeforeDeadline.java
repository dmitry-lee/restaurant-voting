package ru.javaops.topjava2.web.vote;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT2_ID;

@TestPropertySource(properties = {"app.params.vote.time-threshold=23:59:59"})
public class VoteControllerTestBeforeDeadline extends VoteControllerTest {

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    public void updateBeforeDeadline() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        VoteTo updatedTo = new VoteTo(updated);
        perform(MockMvcRequestBuilders
                .put(REST_URL + RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.VOTE_TO_MATCHER.assertMatch(new VoteTo(repository.getById(VoteTestData.USER_VOTE_ID)), updatedTo);
    }
}

