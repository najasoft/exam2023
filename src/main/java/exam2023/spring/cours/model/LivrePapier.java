package exam2023.spring.cours.model;

import jakarta.persistence.Entity;

@Entity
public class LivrePapier extends Livre {

	private String emplacement;

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}
}
