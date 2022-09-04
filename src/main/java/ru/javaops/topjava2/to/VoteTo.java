package ru.javaops.topjava2.to;

import lombok.Getter;
import lombok.Setter;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;

@Getter
@Setter
public class VoteTo extends BaseTo {

    private LocalDate date;

    private Integer userId;

    private Integer restaurantId;

    public VoteTo(Integer id, Integer userId, Integer restaurantId) {
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
