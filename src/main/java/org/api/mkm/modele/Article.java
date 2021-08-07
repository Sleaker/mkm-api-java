package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;

@Data
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idArticle;
	private int idProduct;
	private Localization language;
	private String comments;
	private double price;
	private int count;
	private boolean inShoppingCart;
	private User seller;
	private String condition;
	private boolean foil;
	private boolean signed;
	private boolean playset;
	private boolean altered;
	private Product product;
	private Link links;

	@Override
	public String toString() {
		return String.valueOf(getProduct());
	}

	
	public enum ARTICLES_ATT {
		start, 
		maxResults, 
		userType, 
		minUserScore,
		idLanguage,
		minCondition,
		isFoil,
		isSigned,
		isAltered,
		minAvailable
	}
}