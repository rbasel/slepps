package org.baselfamily.rbasel.slepps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SleppsDataRow {
	private Object[] results;
	private Map<String, Object> columnNameMap;
	
	
	public SleppsDataRow(ResultSet resultSet){
		
		
		
	}
	
	
	/*
	 * 
	 * 
	 * 	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}

	}
	 */

}
