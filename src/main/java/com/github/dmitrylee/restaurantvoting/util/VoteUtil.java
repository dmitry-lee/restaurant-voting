package com.github.dmitrylee.restaurantvoting.util;

import com.github.dmitrylee.restaurantvoting.model.Vote;
import com.github.dmitrylee.restaurantvoting.to.VoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteUtil {

    public static VoteTo getTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDate(), vote.getUser().id(), vote.getRestaurant().id());
    }

}
