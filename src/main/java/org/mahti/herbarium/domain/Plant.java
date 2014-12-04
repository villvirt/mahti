package org.mahti.herbarium.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "plants")
public class Plant extends AbstractPersistable<Long> {

	// TODO
    // user-plant relation: user can have multiple plants, but plants can have only one user
    // fields: date, location (GPS: lat&long)
    // tests (integration+system)

	// scientific names
	private String binomialNomenclature;
	private String family;
	private String name;  // a friendly common name

	private Boolean identified;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;

	@Lob  // tells database to reserve big blocks, optional, runs faster
	private byte[] content;

	public String getBinomialNomenclature() {
		return binomialNomenclature;
	}

	public void setBinomialNomenclature(String binomialNomenclature) {
		this.binomialNomenclature = binomialNomenclature;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIdentified() {
		return identified;
	}

	public void setIdentified(Boolean identified) {
		this.identified = identified;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
