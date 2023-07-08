package exam2023.spring.cours.model;

import jakarta.persistence.Entity;

@Entity
public class Ebook extends Livre {



	private String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
