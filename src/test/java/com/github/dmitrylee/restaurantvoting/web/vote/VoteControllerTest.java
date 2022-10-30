package com.github.dmitrylee.restaurantvoting.web.vote;


import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.repository.VoteRepository;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import com.github.dmitrylee.restaurantvoting.util.VoteUtil;
import com.github.dmitrylee.restaurantvoting.web.AbstractControllerTest;
import com.github.dmitrylee.restaurantvoting.web.restaurant.RestaurantTestData;
import com.github.dmitrylee.restaurantvoting.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.dmitrylee.restaurantvoting.web.vote.VoteTestData.VOTE_TO_MATCHER;
import static com.github.dmitrylee.restaurantvoting.web.vote.VoteTestData.getNew;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {

    public static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    protected VoteRepository repository;

    @Test
    @WithUserDetails(UserTestData.USER2_MAIL)
    public void createNewVote() throws Exception {
        Vote newVote = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.RESTAURANT2_ID)
                .param("restaurantId", Integer.toString(RestaurantTestData.RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo createdTo = VOTE_TO_MATCHER.readFromJson(actions);
        newVote.setId(createdTo.getId());
        VoteTo newVoteTo = VoteUtil.getTo(newVote);
        VOTE_TO_MATCHER.assertMatch(createdTo, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(VoteUtil.getTo(repository.getById(createdTo.getId())), newVoteTo);
    }
}

