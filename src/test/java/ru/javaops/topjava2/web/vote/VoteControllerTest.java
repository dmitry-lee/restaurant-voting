package ru.javaops.topjava2.web.vote;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.TestData.RESTAURANT2_ID;
import static ru.javaops.topjava2.web.vote.VoteTestData.VOTETO_MATCHER;
import static ru.javaops.topjava2.web.vote.VoteTestData.getNew;

public class VoteControllerTest extends AbstractControllerTest {

    public static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    protected VoteRepository repository;

    @Test
    @WithUserDetails(UserTestData.USER2_MAIL)
    public void createNewVote() throws Exception {
        Vote newVote = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders
                .post(REST_URL + RESTAURANT2_ID)
                .param("restaurantId", Integer.toString(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo createdTo = VOTETO_MATCHER.readFromJson(actions);
        newVote.setId(createdTo.getId());
        VoteTo newVoteTo = new VoteTo(newVote);
        VOTETO_MATCHER.assertMatch(createdTo, newVoteTo);
        VOTETO_MATCHER.assertMatch(new VoteTo(repository.getById(createdTo.getId())), newVoteTo);
    }
}

