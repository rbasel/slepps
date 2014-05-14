package org.baselfamily.rbasel.slepps;

import java.sql.Date;
import java.util.List;

public class SleppsTest {

	/**
	 * @param args
	 * @throws SleppsException
	 */
	public static void main(String[] args) throws SleppsException {
		SqlHelper sqlHelper = new SqlHelper();
		sqlHelper.setQuery("select * from feedback.COMMENTS");
		List<SleppsDataRow> results = sqlHelper.executeQuery();

		for (SleppsDataRow dataRow : results) {
			System.out.println(dataRow.getColumnValue("MYUSER"));
			System.out.println(dataRow.getColumnValue(1));
			
			
//			String user = resultSet.getString("myuser");
//			String website = resultSet.getString("webpage");
//			String summary = resultSet.getString("summary");
//			Date date = resultSet.getDate("datum");
//			String comment = resultSet.getString("comments");
		}
	}
}
