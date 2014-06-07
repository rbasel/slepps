package org.baselfamily.rbasel.slepps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataRow implements Iterable<DataColumn> {
	private Map<String, DataColumn> columnMap = new LinkedHashMap<String, DataColumn>();

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

	public void setValue(String columnName, Object value)
			throws SleppsException {
		DataColumn dc = getDataColumn(columnName);
		dc.setValue(value);
	}

	public StatementProperties buildInsertStatement(String tableName) {
		String queryTemplate = "INSERT INTO " + tableName
				+ " (<<ColumnList>>) VALUES (<<ValueList>>);";

		List<Object> parameterValues = new LinkedList<Object>();

		StringBuilder columnList = new StringBuilder();
		StringBuilder parameterList = new StringBuilder();
		String prefix = "";
		for (DataColumn dc : this) {
			columnList.append(prefix + dc.getName());
			parameterList.append(prefix + "?");
			parameterValues.add(dc.getValue());
			prefix = ",";
		}

		String query = queryTemplate.replace("<<ColumnList>>",
				columnList.toString()).replace("<<ValueList>>",
				parameterList.toString());

		return new StatementProperties(query, parameterValues);
	}
}
