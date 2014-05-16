package org.baselfamily.rbasel.slepps;

import java.sql.Date;
import java.util.List;

public class SleppsTest {

	/**
	 * @param args
	 * @throws SleppsException
	 */
	public static void main(String[] args) throws SleppsException {
		Slepps sqlHelper = new Slepps();
		sqlHelper.setQuery("select * from feedback.COMMENTS");
		List<DataRow> results = sqlHelper.executeQuery();

		for (DataRow dataRow : results) {
			for (DataColumn dc : dataRow) {
				System.out.println(dc.getName() + " = " + dc.getValue());
			}

			System.out.println(dataRow.getValue("MYUSER"));
		}
	}
}
