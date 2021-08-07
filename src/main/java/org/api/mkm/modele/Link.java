package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Link implements Serializable {

	private static final long serialVersionUID = 1L;
	private String rel;
	private String href;
	private String method;
	private String action;
	private int idArticle;

}
