package ar.com.alkemylab.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "admins") //esta clase se mapea con la tabla admins
public class Admin extends Person {

    /*Hereda de la clase
    Persona los atributos name, 
    lastName y dni
    */

//Atributos
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String file; // el legajo

    @JsonIgnore
    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private Users user;


    //Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
        user.setAdmin(this);
    }


    
}
