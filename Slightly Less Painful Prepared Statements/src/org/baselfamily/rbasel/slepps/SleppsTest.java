package org.baselfamily.rbasel.slepps;

public class SleppsTest {

	/**
	 * @param args
	 * @throws SleppsException 
	 */
	public static void main(String[] args) throws SleppsException {
		SqlHelper sqlHelper = new SqlHelper();
		sqlHelper.setQuery("select * from feedback.COMMENTS");
		sqlHelper.executeQuery();
	}
}
