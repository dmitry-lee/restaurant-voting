package com.github.dmitrylee.restaurantvoting.web.vote;

import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import com.github.dmitrylee.restaurantvoting.web.MatcherFactory;
import com.github.dmitrylee.restaurantvoting.web.restaurant.RestaurantTestData;
import com.github.dmitrylee.restaurantvoting.web.user.UserTestData;

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
