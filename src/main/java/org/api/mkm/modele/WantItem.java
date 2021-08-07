package org.api.mkm.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WantItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String[] CONDITIONS = { "MT", "NM", "EX", "GD", "LP", "PL", "PO" };

	private String idWant;
	int count;
	double wishPrice;
	private MkmBoolean mailAlert;
	private String type;
	private Product product;
	private List<Integer> idLanguage = new ArrayList<>();
	private String minCondition;
	private MkmBoolean isFoil;
	private MkmBoolean isSigned;
	private MkmBoolean isPlayset;
	private MkmBoolean isAltered;
	private MkmBoolean isFirstEd;// only yugiho
	private Integer idProduct;

	@Override
	public String toString() {
		return String.valueOf(product);
	}

	public MkmBoolean isMailAlert() {
		return mailAlert;
	}

	public void setMailAlert(MkmBoolean mailAlert) {
		this.mailAlert = mailAlert;
	}

	public void setProduct(Product product) {
		this.product = product;

		if (product != null)
			idProduct = product.getIdProduct();

	}

	public MkmBoolean isFoil() {
		return isFoil;
	}

	public void setFoil(MkmBoolean isFoil) {
		this.isFoil = isFoil;
	}

	public MkmBoolean isSigned() {
		return isSigned;
	}

	public void setSigned(MkmBoolean isSigned) {
		this.isSigned = isSigned;
	}

	public MkmBoolean isPlayset() {
		return isPlayset;
	}

	public void setPlayset(MkmBoolean isPlayset) {
		this.isPlayset = isPlayset;
	}

	public MkmBoolean isAltered() {
		return isAltered;
	}

	public void setAltered(MkmBoolean isAltered) {
		this.isAltered = isAltered;
	}

	public MkmBoolean isFirstEd() {
		return isFirstEd;
	}

	public void setFirstEd(MkmBoolean isFirstEd) {
		this.isFirstEd = isFirstEd;
	}

}
