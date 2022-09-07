package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteTo;
import ru.javaops.topjava2.web.MatcherFactory;
import ru.javaops.topjava2.web.restaurant.RestaurantTestData;
import ru.javaops.topjava2.web.user.UserTestData;

import java.time.LocalDate;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int USER_VOTE_ID = 3;

    public static Vote getNew() {
        return new Vote(null, UserTestData.user2, LocalDate.now(), RestaurantTestData.restaurant2);
    }

    public static Vote getUpdated() {
        return new Vote(3, UserTestData.user, LocalDate.now(), RestaurantTestData.restaurant2);
    }
}
