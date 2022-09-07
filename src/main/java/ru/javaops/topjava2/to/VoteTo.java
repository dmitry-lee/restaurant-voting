package ru.javaops.topjava2.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate date;

    Integer userId;

    Integer restaurantId;

    public VoteTo(Integer id, LocalDate date, Integer userId, Integer restaurantId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public VoteTo(Vote vote) {
        super(vote.id());
        this.date = vote.getDate();
        this.userId = vote.getUser().getId();
        this.restaurantId = vote.getRestaurant().getId();
    }
}
