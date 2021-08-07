package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShippingMethod implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idShippingMethod;
	private String name;
	private double price;
	private boolean letter;
	private boolean insured;

}
