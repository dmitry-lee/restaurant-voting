package com.github.dmitrylee.restaurantvoting.web.vote;


import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import com.github.dmitrylee.restaurantvoting.util.JsonUtil;
import com.github.dmitrylee.restaurantvoting.web.restaurant.RestaurantTestData;
import com.github.dmitrylee.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"app.params.vote.time-deadline=00:00:00"})
public class VoteControllerTestAfterDeadline extends VoteControllerTest {

    @Test
    @WithUserDetails(UserTestData.USER_MAIL)
    public void updateAfterDeadline() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        VoteTo updatedTo = voteMapper.voteToVoteDto(updated);
        perform(MockMvcRequestBuilders
                .put(REST_URL + VoteTestData.USER_VOTE_ID)
                .param("restaurantId", String.valueOf(RestaurantTestData.RESTAURANT2_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Voting time for today is over.")));
    }
}

