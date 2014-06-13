package org.baselfamily.rbasel.slepps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

public class Slepps {
	private java.sql.Connection connection = null;
	// private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private StatementProperties statementProperties;

	public Slepps(Connection connection) {
		this.connection = connection;
	}

	public Slepps(String driver, String connectionUrl, String username,
			String password) throws SleppsException {
		try {
			Class.forName(driver);

			Properties info = new Properties();
			info.setProperty("user", username);
			info.setProperty("password", password);

			connection = DriverManager.getConnection(connectionUrl, info);

		} catch (ClassNotFoundException e) {
			throw new SleppsException("Unable to load SQL driver", e);
		} catch (SQLException e) {
			throw new SleppsException("Error retrieving connection for URL- "
					+ connectionUrl, e);
		}
	}

	public void setQuery(String query) throws SleppsException {
		statementProperties = new StatementProperties(query);
	}

	public void setParameter(int parameterIndex, Object value)
			throws SleppsException {
		statementProperties.setParameter(parameterIndex, value);
	}

	private PreparedStatement buildPreparedStatement() throws SleppsException {
		if (statementProperties == null){
			throw new SleppsException("Query not set.  The query must be set before executing a statement.", null );
		}
		
		if (statementProperties.nullParametersExist()) {
			throw new SleppsException("Not all query parameters are set in the following:\n\t" + statementProperties.approximateSqlStatement(), null);
		}
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(statementProperties
					.getQuery());
		} catch (SQLException e) {
			throw new SleppsException(
					"Error retrieving preparedStatement from connection for query "
							+ statementProperties.getQuery(), e);
		}

		for (int i = 0; i < statementProperties.getQueryParameters().length; i++) {
			try {
				preparedStatement.setObject(i + 1,
						statementProperties.getQueryParameters()[i]);
			} catch (SQLException e) {
				throw new SleppsException(
						"Error setting preparedStatementValue for parameter "
								+ (i + 1), e);
			}
		}

		return preparedStatement;

	}

	public List<DataRow> executeQuery(StatementProperties statementProperties)
			throws SleppsException {
		this.statementProperties = statementProperties;
		return executeQuery();
	}

	public List<DataRow> executeQuery() throws SleppsException {
		PreparedStatement preparedStatement = buildPreparedStatement();

		try {
			resultSet = preparedStatement.executeQuery();
			return transformResultSet(resultSet);
		} catch (SQLException e) {
			throw new SleppsException("Error executing prepared statement: "
					+ statementProperties.approximateSqlStatement(), e);
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(preparedStatement);
		}
	}

	public int executeUpdate(StatementProperties statementProperties)
			throws SleppsException {
		this.statementProperties = statementProperties;
		return executeUpdate();
	}

	public int executeUpdate() throws SleppsException {
		PreparedStatement preparedStatement = buildPreparedStatement();

		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SleppsException("Error executing prepared statement: "
					+ statementProperties.approximateSqlStatement(), e);
		} finally {
			DbUtils.closeQuietly(preparedStatement);
		}
	}

	public void closeConnection() {
		DbUtils.closeQuietly(connection);
	}

	private List<DataRow> transformResultSet(ResultSet resultSet)
			throws SleppsException {

		List<DataRow> dataTable = new ArrayList<DataRow>();
		try {
			while (resultSet.next()) {
				dataTable.add(new DataRow(resultSet));
			}
		} catch (SQLException e) {
			throw new SleppsException("Error transforming resultSet", e);
		}
		return dataTable;

	}

	public String approximateSqlStatement() {
		return statementProperties.approximateSqlStatement();
	}
}
