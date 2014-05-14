package org.baselfamily.rbasel.slepps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

public class SqlHelper {
	private java.sql.Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String query;
	private String[] queryParameters;

	public SqlHelper(Connection connection) {
		this.connection = connection;
	}

	public SqlHelper() throws SleppsException {
		this("jdbc:mysql://localhost/feedback", "sqluser", "sqluserpw");
	}

	public SqlHelper(String connectionUrl, String username, String password)
			throws SleppsException {

		String connectionString = connectionUrl + "?user=" + username
				+ "&password=" + password;
		System.out.println(connectionString);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e) {
			throw new SleppsException("Unable to load SQL driver", e);
		} catch (SQLException e) {
			throw new SleppsException("Error retrieving connection for URI "
					+ connectionUrl, e);
		}
	}

	public void setQuery(String query) throws SleppsException {
		this.query = query;
		int count = StringUtils.countMatches(query, "?");
		queryParameters = new String[count];
		try {
			preparedStatement = connection.prepareStatement(query);
		} catch (SQLException e) {
			throw new SleppsException(
					"Unable to generate prepared statement with Query ["
							+ query + "]", e);
		}
	}

	public SleppsResultSet executeQuery() throws SleppsException {
		if (nullParametersExist()) {
			throw new SleppsException("Not all query parameters are set", null);
		}

		try {
			resultSet = preparedStatement.executeQuery();
			return new SleppsResultSet(resultSet);
		} catch (SQLException e) {
			throw new SleppsException("Error executing prepared statement", e);
		} finally {
			close(resultSet);
			close(preparedStatement);
		}
	}

	public void setString(int parameterIndex, String theString)
			throws SleppsException {
		queryParameters[parameterIndex - 1] = theString;
		try {
			preparedStatement.setString(parameterIndex, theString);
		} catch (IndexOutOfBoundsException e) {
			String description = "Trying to set parameter " + parameterIndex
					+ " when only " + queryParameters.length + " are expected";
			throw new SleppsException(description, e);
		} catch (SQLException e) {
			throw new SleppsException("Error setting query parameter", e);
		}
	}

	private boolean nullParametersExist() {
		for (int i = 0; i < queryParameters.length; i++) {
			if (queryParameters[i] == null)
				return true;
		}

		return false;
	}

	private void close(AutoCloseable autoCloseable) {
		try {
			if (autoCloseable != null)
				autoCloseable.close();
		} catch (Exception e) {
			// Don't do anything. So as not to interfere with other closures.
		}
	}

}
