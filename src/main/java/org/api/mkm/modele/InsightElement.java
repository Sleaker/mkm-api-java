package org.api.mkm.modele;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsightElement implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ed;
	private String cardName;
	private double price;
	private double yesterdayPrice;
	private double changeValue;
	private String url;
	private int stock;
	private int yesterdayStock;

	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return getCardName();
		}
	}

}
