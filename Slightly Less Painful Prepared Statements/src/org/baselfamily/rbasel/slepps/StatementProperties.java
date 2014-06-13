package org.baselfamily.rbasel.slepps;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StatementProperties {
	private String query;
	private Object[] queryParameters;

	public StatementProperties(String query) {
		this.query = query;
		int count = StringUtils.countMatches(query, "?");
		queryParameters = new Object[count];
	}

	StatementProperties(String query2, List<Object> parameterValues) {
		this.query = query2;
		this.queryParameters = parameterValues.toArray();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Object[] getQueryParameters() {
		return queryParameters;
	}

	public void setParameter(int parameterIndex, Object value)
			throws SleppsException {
		try {
			queryParameters[parameterIndex - 1] = value;
		} catch (IndexOutOfBoundsException e) {
			String description = "Trying to set parameter " + parameterIndex
					+ " when only " + queryParameters.length + " are expected";
			throw new SleppsException(description, e);
		}
	}

	public boolean nullParametersExist() {
		for (int i = 0; i < queryParameters.length; i++) {
			if (queryParameters[i] == null)
				return true;
		}

		return false;
	}

	public String approximateSqlStatement() {
		StringBuffer returnValue = new StringBuffer(query);
		for (int i = 0; i < queryParameters.length; i++) {
			int location = returnValue.indexOf("?");
			String approximatedValue = approximateQueryParameter(queryParameters[i]);
			if (approximatedValue == null){
				approximatedValue = "<Missing Paramenter # " + (i  + 1) + ">";
			}
			
			returnValue.replace(location, (location + 1), approximatedValue
					);
		}
		return returnValue.toString();
	}

	private String approximateQueryParameter(Object queryParameter) {
		if (queryParameter instanceof Number) {
			return queryParameter.toString();
		}

		if (queryParameter instanceof java.util.Date) {
			return "'"
					+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
							.format((java.util.Date) queryParameter) + "'";
		}

		return (queryParameter == null) ? null : "'"
				+ queryParameter.toString() + "'";

	}
}
