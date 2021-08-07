package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private User partner;
	private Message message;
	private int unreadMessages;
	private List<Link> links;

	@Override
	public String toString() {
		return partner + ":" + message;
	}

}
