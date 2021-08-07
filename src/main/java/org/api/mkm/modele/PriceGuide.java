package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceGuide implements Serializable {

	private static final long serialVersionUID = 1L;
	// TODO: violates naming schemes - should be camel-cased
	private double SELL;
	private double LOW;
	private double LOWEX;
	private double LOWFOIL;
	private double AVG;
	private double TREND;

	@Override
	public String toString() {
		return "AVG=" + AVG + ", LOWEX=" + LOWEX + ", LOWFOIL=" + LOWFOIL + ", SELL=" + SELL + ", LOW=" + LOW
				+ ", TREND=" + TREND;
	}

}
