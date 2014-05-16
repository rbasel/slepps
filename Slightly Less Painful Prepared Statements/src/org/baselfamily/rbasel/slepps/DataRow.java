package org.baselfamily.rbasel.slepps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataRow implements Iterable<DataColumn> {
	Map<String, DataColumn> columnMap = new LinkedHashMap<String, DataColumn>();

	public DataRow(ResultSet resultSet) throws SQLException {
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			DataColumn dc = new DataColumn(resultSet.getMetaData()
					.getColumnName(i), resultSet.getObject(i));
			columnMap.put(dc.getName(), dc);
		}
	}

	@Override
	public Iterator<DataColumn> iterator() {
		return columnMap.values().iterator();
	}

	public DataColumn getDataColumn(String columnName) throws SleppsException {

		DataColumn dc = columnMap.get(columnName);
		if (dc.equals(null)) {
			throw new SleppsException(
					"Can not find dataColumn for column name [" + columnName
							+ "]", null);
		}
		return dc;
	}

	public Object getValue(String columnName) throws SleppsException {
		DataColumn dc = getDataColumn(columnName);
		return dc.getValue();
	}
}
