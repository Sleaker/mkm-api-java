package org.api.mkm.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Basket implements Serializable {

	private static final long serialVersionUID = 1L;
	private Address shippingAddress;
	private List<ShoppingCart> shoppingCart = new ArrayList<>();
	private User account;

	@Override
	public String toString() {
		return getAccount() + " " + shoppingCart.size() + " cart(s)";
	}

}
