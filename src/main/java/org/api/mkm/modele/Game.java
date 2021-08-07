package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idGame;
	private String name;
	private String abbreviation;
	private Link links;

	@Override
	public String toString() {
		return getName();
	}
}
