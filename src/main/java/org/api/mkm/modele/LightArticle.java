package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LightArticle implements Serializable {

	private int idArticle;
	private int idProduct;
	private Localization language;
	private String comments;
	private double price;
	private int count;
	private boolean inShoppingCart;
	private double priceEUR;
	private double priceGBP;
	private LightProduct product;
	private String condition;
	private boolean foil;
	private boolean signed;
	private boolean playset;
	private boolean altered;

	@Override
	public String toString() {
		if (getProduct() != null)
			return getProduct().toString();

		return super.toString();
	}
}
