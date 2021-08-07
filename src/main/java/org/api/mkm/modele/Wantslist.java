package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Wantslist implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idWantsList;
	private Game game;
	private String name;
	private int itemCount;
	private List<WantItem> item;
	private List<Link> links;

	@Override
	public String toString() {
		return getName();
	}

}
