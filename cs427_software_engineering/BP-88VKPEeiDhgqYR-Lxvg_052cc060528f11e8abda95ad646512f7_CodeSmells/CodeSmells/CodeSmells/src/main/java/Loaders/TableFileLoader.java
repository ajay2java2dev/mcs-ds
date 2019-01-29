package Loaders;

import Entities.SingleTable;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableFileLoader {

    private static final String[] HEADERS = {"Table Number", "Number of Seats"};
    private String fileName;

    private static final String INVALID_ARGS = "The specified Columns are incorrect.";
    private static final String UTF8 = "UTF-8";

    public TableFileLoader(String fileName) {
        this.fileName = fileName;
    }

    public File getDefaultFile() {
        return new File("./tables.txt");
    }

    public List<SingleTable> load() {
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

    private List<SingleTable> createMenuItems(List<List<String>> result) {
        List<SingleTable> tables = new ArrayList<SingleTable>();
        if (result != null && !result.isEmpty()) {
            for (List<String> line : result) {
                int tableIndex = Integer.parseInt(line.get(0).trim());
                int tableSeats = Integer.parseInt(line.get(1).trim());
                SingleTable table = new SingleTable(tableIndex, tableSeats);
                tables.add(table);
            }
        }
        return tables;
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
