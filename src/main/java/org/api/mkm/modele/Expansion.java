package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Expansion implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idExpansion;
	private String enName;
	private int expansionIcon;
	private String abbreviation;
	private String icon;
	private String releaseDate;
	private boolean released;
	private int idGame;
	private Link links;


	@Override
	public String toString() {
		return getEnName();
	}
	
}
