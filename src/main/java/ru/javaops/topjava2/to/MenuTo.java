package ru.javaops.topjava2.to;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MenuTo extends BaseTo {

    @NotNull
    private LocalDate date;

    @NotEmpty
    @Valid
    private List<DishTo> dishes;

    public MenuTo(Integer id, LocalDate date, List<DishTo> dishes) {
        super(id);
        this.date = date;
        this.dishes = dishes;
    }
}
