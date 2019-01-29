package Loaders;

import Entities.MenuItem;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuFileLoader {

    private static final String[] HEADERS = {"MenuItem Name", "Type", "Price", "Calorie"};
    private String fileName;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    private static final String INVALID_ARGS = "The specified Columns are incorrect.";
    private static final String UTF8 = "UTF-8";

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
            handleInputOutputConn(breader);
        }
        return createMenuItems(result);
    }

    private List<MenuItem> createMenuItems(List<List<String>> result) {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        if (result != null && !result.isEmpty()) {
            for (List<String> line : result) {
                String dishName = line.get(ZERO).trim();
                String dishType = line.get(ONE).toUpperCase().trim();
                double dishPrice = Double.parseDouble(line.get(TWO).trim());
                double dishCalorie = Double.parseDouble(line.get(THREE).trim());
                MenuItem menuItem = new MenuItem(dishName, dishType, dishPrice, dishCalorie);
                menuItems.add(menuItem);
            }
        }
        return menuItems;
    }

    private void handleInputOutputConn(BufferedReader breader) {
        try {
            if (breader != null) {
                breader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
