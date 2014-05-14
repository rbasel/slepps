package org.baselfamily.rbasel.slepps;

public class SleppsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SleppsException(String description, Throwable e) {
		super(description, e);
	}
}