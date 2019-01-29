package Components;

import java.util.List;

import Entities.MenuItem;
import Loaders.MenuFileLoader;

public class Menu {

	private List<MenuItem> menuItems;

	public Menu(String fileName) {
		MenuFileLoader menuLoader = new MenuFileLoader(fileName);
		menuItems = menuLoader.load();
	}

	public MenuItem[] getAllDishes() {
		return menuItems.toArray(new MenuItem[] {});
	}

	public MenuItem getItemByName(String name) {
		for (MenuItem item : getAllDishes()) {
			if (item.getDishName().equals(name)) {
				return item;
			}
		}
		return null;
	}

}
