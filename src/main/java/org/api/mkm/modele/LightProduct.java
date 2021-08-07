package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LightProduct implements Serializable {
	private int idGame;
	private String enName;
	private String locName;
	private String image;
	private String expansion;
	private String nr;
	private Integer expIcon;
	private String rarity;

	@Override
	public String toString() {
		return getEnName();
	}

}
