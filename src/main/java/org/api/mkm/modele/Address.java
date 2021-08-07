package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;

@Data
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String extra;
	private String street;
	private String zip;
	private String city;
	private String country;

	@Override
	public String toString() {
		return getName() + " " + getStreet() + " " + getZip() + " " + getCity();
	}
}
