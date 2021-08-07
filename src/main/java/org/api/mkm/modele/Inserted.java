package org.api.mkm.modele;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Inserted {

	private boolean success;
	private LightArticle idArticle;
	private LightArticle tried;
	private String error;
	
}
