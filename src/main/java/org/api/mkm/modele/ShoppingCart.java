package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingCart implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idReservation;
	private boolean buyer;
	private User seller;
	private List<Article> article;
	
	private double articleValue;
	private double articleCount;
	
	private ShippingMethod shippingMethod;
	
	private double totalValue;
	
}
