package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int START_SEQ = 1;

    public static final int RESTAURANT1_ID = START_SEQ;
    public static final int RESTAURANT2_ID = START_SEQ + 1;
    public static final int RESTAURANT3_ID = START_SEQ + 2;
    public static final int NOT_FOUND_ID = START_SEQ + 999;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Italy");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Capuletti");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Плюшкин");

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant2.id(), restaurant2.getName(), restaurant2.getDishes());
        updated.setName("Updated");
        return updated;
    }
}
