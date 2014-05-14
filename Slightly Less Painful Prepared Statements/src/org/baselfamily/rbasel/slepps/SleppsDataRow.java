package org.baselfamily.rbasel.slepps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SleppsDataRow {
	private Object[] results;
	private Map<String, Integer> columnNameMap;

	public SleppsDataRow(ResultSet resultSet) throws SQLException {
		int length = resultSet.getMetaData().getColumnCount();
		results = new Object[length];
		columnNameMap = new HashMap<String, Integer>();

		for (int i = 1; i <= length; i++) {
			results[i-1] = resultSet.getObject(i);
			String columnName = resultSet.getMetaData().getColumnName(i);
			columnNameMap.put(columnName, i-1);
		}
	}

	public Object getColumnValue(int columnIndex) {
		return results[columnIndex];
	}

	public Object getColumnValue(String columnName) throws SleppsException {
		Integer columnIndex = columnNameMap.get(columnName);
		if (columnIndex == null) {
			throw new SleppsException("Column " + columnName
					+ " does not exist", null);
		}
		return results[columnIndex];
	}

}
