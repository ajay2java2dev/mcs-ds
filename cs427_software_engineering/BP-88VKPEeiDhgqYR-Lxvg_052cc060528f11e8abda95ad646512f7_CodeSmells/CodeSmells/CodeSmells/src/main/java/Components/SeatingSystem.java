package Components;

import Entities.SingleTable;
import Loaders.TableFileLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatingSystem {

    private String tableConfigFilePath;
    private Map<SingleTable, Boolean> tables;

    public SeatingSystem(String tableConfigFilePath) {
        this.tableConfigFilePath = tableConfigFilePath;
        populateSeats();
    }

    private void populateSeats() {
        TableFileLoader loader = new TableFileLoader(tableConfigFilePath);
        List<SingleTable> tableList = loader.load();
        tables = new HashMap<SingleTable, Boolean>();
        for (SingleTable i : tableList) {
            tables.put(i, false);
        }
    }

    public int[] getNumberOfSeatsForAllTable() {
        int[] seats = new int[tables.size()];
        int i = 0;
        for (SingleTable table : tables.keySet()) {
            seats[i] = table.getNumberOfSeats();
            i++;
        }
        return seats;
    }

    public int[] getIndexForAllTable() {
        int[] indices = new int[tables.size()];
        int i = 0;
        for (SingleTable table : tables.keySet()) {
            indices[i] = table.getIndex();
            i++;
        }
        return indices;
    }

    public SingleTable getAvailableTable(int numberOfPeople) {
        SingleTable result = null;
        for (Map.Entry<SingleTable, Boolean> entry : tables.entrySet()) {
            int numOfSeats = entry.getKey().getNumberOfSeats();
            if (!entry.getValue() && numOfSeats >= numberOfPeople
                    && (result == null || numOfSeats < result.getNumberOfSeats())) {
                result = entry.getKey();
            }
        }
        return result;
    }

    public boolean occupy(SingleTable table) {
        boolean val;
        if (tables.get(table)) {
            val = false;
        } else {
            tables.put(table, true);
            val = true;
        }
        return val;
    }

    public boolean vacate(SingleTable table) {
        boolean val;
        if (!tables.get(table)) {
            val = false;
        } else {
            tables.put(table, false);
            val = true;
        }
        return val;
    }

}
