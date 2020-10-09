package ar.com.alkemylab.entities;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.*;

@Entity
@Table(name="students") //Se mapea con la tabla students de la BD
public class Student extends Person{ 
    
    /*Hereda de la clase
    Persona los atributos name, 
    lastName y dni
    */

    //Atributos
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String file;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Enrollment> enrollments = new ArrayList();
    @JsonIgnore
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
        user.setStudent(this);
    }


}
