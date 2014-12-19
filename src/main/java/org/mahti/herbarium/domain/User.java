package org.mahti.herbarium.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class User extends AbstractPersistable<Long>{

    @NotBlank
    @Length(min = 3, max = 30)
    @Column(unique=true)
    private String username;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String salt;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Length(min = 2, max = 30)
    private String name;
    
    @Length(max = 255)
    private String description;

    @OneToMany
    private List<Plant> plants;


    public List<Plant> getPlants() {
            return plants;
    }

    public void setPlants(List<Plant> plants) {
            this.plants = plants;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
