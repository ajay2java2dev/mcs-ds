package Loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Entities.MenuItem;

public class MenuFileLoader {

	private static final String[] HEADERS = { "MenuItem Name", "Type", "Price", "Calorie" };
	private String fileName;

	private static final String INVALID_ARGS = "The specified Columns are incorrect.";
	private static final String UTF8 = "UTF-8";
	private static final int LNGTH_0 = 0;
	private static final int LNGTH_1 = 0;

	public MenuFileLoader(String fileName) {
		this.fileName = fileName;
	}

	public File getDefaultFile() {
		return new File("./menu.txt");
	}

	public List<MenuItem> load() {
		int numberOfColumns = HEADERS.length;
		BufferedReader breader = null;
		File file;
		List<List<String>> result = new ArrayList<List<String>>();

		try {
			file = new File(fileName);
			if (!file.exists()) {
				file = getDefaultFile();
			}
			breader = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF8));
			String line = breader.readLine();
			while (line != null) {
				String[] entries = line.split(",");
				List<String> lineEntry = new ArrayList<String>();

				if (entries.length != numberOfColumns) {
					throw new IllegalArgumentException(INVALID_ARGS);
				}
				for (String entry : entries) {
					lineEntry.add(entry);
				}
				result.add(lineEntry);
				line = breader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			handleIO(breader);
		}
		return createMenuItems(result);
	}

	private List<MenuItem> createMenuItems(List<List<String>> result) {
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		if (result != null && !result.isEmpty()) {
			for (List<String> line : result) {
				String dishName = line.get(0).trim();
				String dishType = line.get(1).toUpperCase().trim();
				double dishPrice = Double.parseDouble(line.get(2).trim());
				double dishCalorie = Double.parseDouble(line.get(3).trim());
				MenuItem menuItem = new MenuItem(dishName, dishType, dishPrice, dishCalorie);
				menuItems.add(menuItem);
			}
		}
		return menuItems;
	}

	private void handleIO(BufferedReader breader) {
		try {
			if (breader != null) {
				breader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
