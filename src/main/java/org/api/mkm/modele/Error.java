package org.api.mkm.modele;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error implements Serializable {

	private static final long serialVersionUID = 1L;
	private String error;
	private String details;
	
	@Override
	public String toString() {
		return getError() +":"+getDetails();
	}	
}
