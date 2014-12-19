package org.mahti.herbarium.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Plant extends AbstractPersistable<Long> {

    // TODO
    // fields: date, location (GPS: lat&long)

    private long correct;
    private long incorrect;
    // see who have voted and prevent them to vote again
    // List<User> usersVoted;  // or should be String username?

    private boolean identified;

    // names
    @NotBlank
    @Length(min = 2, max = 30)
    private String name;  // kansankielellä
    private String family;  //  heimo
    private String genus;  // suku
    private String species;  // laji
    // binomial nomenclature: genus + species

    @Lob  // tells database to reserve big blocks, optional, runs faster
    private byte[] content;
        
    private String username;

    @Temporal(TemporalType.DATE)
    private Date date;

    // @TODO location (GPS: lat&long)
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
        
    public void setUser(String user) {
        this.username=user;
    }
    
    public List<Comment> getComments(){
        return comments;
    }
    
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

}
