package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idOrder;
	private boolean isBuyer;
	private User seller;
	private User buyer;
	private OrderState state;
	private ShippingMethod shippingMethod;
	private boolean presale;
	private Address shippingAddress;
	private int articleCount;
	private String note;
	private Evaluation evaluation;
	private List<LightArticle> article;
	private double articleValue;
	private double serviceFeeValue;
	private double totalValue;
	private String trackingNumber;
	private String currencyCode;

	@Override
	public String toString() {
		return String.valueOf(getIdOrder());
	}

	// TODO: this is bad naming, conflicts with User buyer
	public boolean isBuyer() {
		return isBuyer;
	}

	public void setBuyer(boolean isBuyer) {
		this.isBuyer = isBuyer;
	}


}
