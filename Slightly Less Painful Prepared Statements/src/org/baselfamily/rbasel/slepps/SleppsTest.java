package org.baselfamily.rbasel.slepps;

import java.util.List;

public class SleppsTest {

	/**
	 * @param args
	 * @throws SleppsException
	 */
	public static void main(String[] args) throws SleppsException {
		Slepps sqlHelper = new Slepps();
		sqlHelper.setQuery("select * from feedback.COMMENTS");
		System.out.println(sqlHelper.approximateSqlStatement());
		List<DataRow> results = sqlHelper.executeQuery();

		Slepps employeeHelper = new Slepps("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost/employees", "sqluser", "sqluserpw");
		employeeHelper.setQuery("SELECT * FROM employees.departments;");
		results = employeeHelper.executeQuery();
		for (DataRow dataRow : results) {
			for (DataColumn dc : dataRow) {
				System.out.print(dc.getValue() + " ");
			}
			System.out.println();
		}
		String query = "SELECT * FROM employees.departments "
				+ "where dept_name in (?, ?);";

		employeeHelper.setQuery(query);
		employeeHelper.setString(1, "Development");
		employeeHelper.setString(2, "Marketing");
		System.out.println(employeeHelper.approximateSqlStatement());
		results = employeeHelper.executeQuery();
		printResults(results);

	}

	private static void printResults(List<DataRow> results) {
		for (DataRow dataRow : results) {
			for (DataColumn dc : dataRow) {
				System.out.print(dc.getValue() + " ");
			}
			System.out.println();
		}
	}

}
