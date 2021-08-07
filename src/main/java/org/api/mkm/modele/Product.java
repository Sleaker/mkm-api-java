package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idProduct;
	private String idMetaproduct;
	private int idGame;
	private Integer countReprints;
	private String enName;
	private List<Localization> localization;
	private String website;
	private String image;
	private String gameName;
	private String categoryName;
	private String number;
	private String rarity;
	private String expansionName;
	private Expansion expansion;
	private PriceGuide priceGuide;
	private List<Expansion> reprint;
	private List<Link> links;
	private Category category;

	@Override
	public String toString() {
		return getEnName();
	}

	public enum PRODUCT_ATTS {
		exact, idGame, idLanguage, start, maxResults
	}
}
