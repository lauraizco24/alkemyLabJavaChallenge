package ar.com.alkemylab.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/* Esta clase contiene atributos 
generales que son heredados 
por la clase teacher, admin 
y student*/
@MappedSuperclass
public class Person {
    // atributos
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private Integer dni;

    // getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

}
