package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1 - English 
 * 2 - French 
 * 3 - German 
 * 4 - Spanish 
 * 5 - Italian 
 * 6 - Simplified Chinese 
 * 7 - Japanese 
 * 8 - Portuguese 
 * 9 - Russian 
 * 10 - Korean 
 * 11 - Traditional Chinese
 */
@Data
@NoArgsConstructor
public class Localization implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idLanguage;
	private String name;
	private String languageName;

	public Localization(int id, String languageName) {
		this.idLanguage = id;
		this.languageName = languageName;
	}

	@Override
	public String toString() {
		return getLanguageName();
	}

}
