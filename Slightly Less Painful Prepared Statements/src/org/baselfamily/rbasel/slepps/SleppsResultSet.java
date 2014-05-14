package org.baselfamily.rbasel.slepps;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SleppsResultSet {

	public SleppsResultSet(ResultSet resultSet) throws SleppsException {
	
		try {
			writeResultSet(resultSet);
		} catch (SQLException e) {
throw new SleppsException("something bad happened", e);
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

}
