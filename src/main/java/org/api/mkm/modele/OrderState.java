package org.api.mkm.modele;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderState implements Serializable {

	private static final long serialVersionUID = 1L;
	private String state;
	private Date dateBought;
	private Date datePaid;
	private Date dateSent;
	private Date dateReceived;

	@Override
	public String toString() {
		return getState();
	}

}
