package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.to.MenuTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuUtil {

    public static MenuTo getTo(Menu menu) {
        return new MenuTo(menu.id(), menu.getDate(), DishUtil.getTos(menu.getDishes()));
    }

    public static List<MenuTo> getTos(List<Menu> menus) {
        return menus.stream().map(MenuUtil::getTo).collect(Collectors.toList());
    }

    public static Menu getFromTo(MenuTo menuTo) {
        return new Menu(menuTo.id(), menuTo.getDate(), DishUtil.getFromTos(menuTo.getDishes()));
    }
}
