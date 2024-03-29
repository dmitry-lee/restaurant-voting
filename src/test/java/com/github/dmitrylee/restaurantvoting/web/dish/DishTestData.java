package com.github.dmitrylee.restaurantvoting.web.dish;

import com.github.dmitrylee.restaurantvoting.model.Dish;
import com.github.dmitrylee.restaurantvoting.to.DishTo;
import com.github.dmitrylee.restaurantvoting.web.MatcherFactory;

import java.util.List;

public class DishTestData {

    public static MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingRecursiveComparator(DishTo.class);
    public static MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menuDate", "restaurant");

    public static final int START_SEQ = 1;

    public static final int DISH1_ID = START_SEQ;
    public static final int DISH2_ID = START_SEQ + 1;
    public static final int DISH3_ID = START_SEQ + 2;
    public static final int DISH4_ID = START_SEQ + 3;
    public static final int DISH5_ID = START_SEQ + 4;
    public static final int DISH6_ID = START_SEQ + 5;
    public static final int DISH7_ID = START_SEQ + 6;
    public static final int DISH8_ID = START_SEQ + 7;
    public static final int DISH9_ID = START_SEQ + 8;
    public static final int NEW_DISH_ID = START_SEQ + 9;
    public static final Dish dish1 = new Dish(DISH1_ID, "Bruschetta with chicken pate and cream cheese", 390);
    public static final Dish dish2 = new Dish(DISH2_ID, "Greek salad with chickpeas and feta", 690);
    public static final Dish dish3 = new Dish(DISH3_ID, "Tom yam with coconut milk and shrimp", 850);
    public static final Dish dish4 = new Dish(DISH4_ID, "Salmon tartare", 890);
    public static final Dish dish5 = new Dish(DISH5_ID, "Spaghetti with cherry tomatoes and basil", 390);
    public static final Dish dish6 = new Dish(DISH6_ID, "Risotto with seafood", 990);
    public static final Dish dish7 = new Dish(DISH7_ID, "Борщ с говядиной, черным хлебом и салом", 530);
    public static final Dish dish8 = new Dish(DISH8_ID, "Манты с телятиной", 470);
    public static final Dish dish9 = new Dish(DISH9_ID, "Бефстроганов с картофельным пюре", 900);

    public static List<Dish> restaurant1Menu = List.of(dish1, dish2, dish3);
    public static List<Dish> restaurant2Menu = List.of(dish6, dish4, dish5);
    public static List<Dish> restaurant3Menu = List.of(dish9, dish7, dish8);

    public static Dish getNew() {
        return new Dish(null, "New dish", 100);
    }

    public static Dish getUpdated() {
        Dish updated = dish2;
        updated.setName("Updated dish name");
        return updated;
    }
}
