package Components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.SingleTable;
import Loaders.TableFileLoader;

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
		for (SingleTable i : tableList)
			tables.put(i, false);

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
		boolean isOccupied = false;
		if (!tables.get(table)) {
			tables.put(table, true);
			isOccupied = true;
		}
		return isOccupied;
	}

	public boolean vacate(SingleTable table) {
		boolean isVacant = false;
		if (tables.get(table)) {
			tables.put(table, false);
			isVacant = true;
		}
		return isVacant;
	}

}
