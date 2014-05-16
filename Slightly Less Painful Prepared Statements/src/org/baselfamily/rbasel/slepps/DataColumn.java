package org.baselfamily.rbasel.slepps;

public class DataColumn {
	private String name;
	private Object value;

	public DataColumn(String name, Object value) {
		this.setName(name);
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
