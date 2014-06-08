package org.baselfamily.rbasel.slepps;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SleppsTest {
	static List<DataRow> results;
	static String query;
	static Slepps employeeHelper;
	static Slepps sqlHelper;

	public static void main(String[] args) throws SleppsException, SQLException {
		employeeHelper = new Slepps("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost/employees", "sqluser", "sqluserpw");

		testSomething();
		testEmployee();
		testInsertStatement();
	}

	public static void testSomething() throws SleppsException, SQLException {
		query = "SELECT * FROM employees.dept_emp where from_date > ?;";
		employeeHelper.setQuery(query);

		Calendar cal = Calendar.getInstance();
		cal.set(2000, Calendar.JANUARY, 1);

		employeeHelper.setParameter(1, cal.getTime());

		results = employeeHelper.executeQuery();
		System.out.println(employeeHelper.approximateSqlStatement());
		printResults(results,5);
	}

	public static void testEmployee() throws SleppsException, SQLException {

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
		employeeHelper.setParameter(1, "Development");
		employeeHelper.setParameter(2, "Marketing");
		System.out.println(employeeHelper.approximateSqlStatement());
		results = employeeHelper.executeQuery();
		printResults(results, 10);
	}

	public static void testInsertStatement() throws SleppsException,
			SQLException {
		employeeHelper.setQuery("SELECT * FROM employees.dept_emp;");
		results = employeeHelper.executeQuery();
		for (DataRow dr : results) {
			StatementProperties insertStatement = dr
					.buildInsertStatement("employees.dept_emp22");
			System.out.println(insertStatement.getQuery());
			System.out.println(Arrays.toString(insertStatement
					.getQueryParameters()));
		}

	}

	private static void printResults(List<DataRow> results, int limit) {
		for (int i = 0; i < results.size() && i < limit; i++) {
			for (DataColumn dc : results.get(i)) {
				System.out.print(dc.getValue() + " ");
			}
			System.out.println();
		}
	}

}
