package ru.javaops.topjava2.web.menu;

import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.MenuTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.web.TestData.*;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);
    public static final MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuTo.class);

    public static final int MENU1_ID = START_SEQ;
    public static final int MENU2_ID = START_SEQ + 1;
    public static final int MENU3_ID = START_SEQ + 2;
    public static final int NEW_MENU_ID = START_SEQ + 3;
    public static final Menu menu1 = new Menu(MENU1_ID, restaurant1, LocalDate.of(2022, 8, 20), List.of(dish1, dish2, dish3));
    public static final Menu menu2 = new Menu(MENU2_ID, restaurant2, LocalDate.now(), List.of(dish4, dish5, dish6));
    public static final Menu menu3 = new Menu(MENU3_ID, restaurant3, LocalDate.now(), List.of(dish7, dish8, dish9));
    public static final Menu newMenu = new Menu(NEW_MENU_ID, restaurant1, LocalDate.now(), List.of(newDish));
}
