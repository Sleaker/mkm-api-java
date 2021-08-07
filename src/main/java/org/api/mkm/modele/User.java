package org.api.mkm.modele;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idUser;
	private String username;
	private Date registrationDate;
	private boolean isCommercial;
	private boolean isSeller;
	private Address address;
	private String phone;
	private String email;
	private String vat;
	private String legalInformation;
	private int riskGroup;
	private String lossPercentage;
	private int unsentShipments;
	private int reputation;
	private int shipsFast;
	private int sellCount;
	private int soldItems;
	private int avgShippingTime;
	private boolean onVacation;

	@Override
	public String toString() {
		return getUsername();
	}

	public boolean getIsCommercial() {
		return isCommercial;
	}

	public void setIsCommercial(boolean isCommercial) {
		this.isCommercial = isCommercial;
	}

	public boolean getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(boolean isSeller) {
		this.isSeller = isSeller;
	}
}
