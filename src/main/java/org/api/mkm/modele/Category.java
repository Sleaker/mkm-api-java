package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idCategory;
	private String categoryName;
	
	@Override
	public String toString() {
		return getCategoryName();
	}
}
