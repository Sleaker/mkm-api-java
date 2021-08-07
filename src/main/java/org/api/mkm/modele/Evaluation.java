package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Evaluation implements Serializable {

	private static final long serialVersionUID = 1L;
	private int evaluationGrade;
	private int itemDescription;
	private int packaging;
	private int speed;
	private String comment;
	private List<String> complaint;
	
}
