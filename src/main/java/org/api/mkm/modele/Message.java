package org.api.mkm.modele;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idMessage;
	private boolean sending;
	private Date date;
	private String text;
	private boolean unread;
	
	@Override
	public String toString() {
		return getText();
	}	
}
