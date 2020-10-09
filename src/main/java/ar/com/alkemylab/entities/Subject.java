package ar.com.alkemylab.entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="subjects") // se mapea con la tabla subjects
public class Subject {
    //Atributos
    
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_name")
    private String subjectName;

    private String schedule;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @Column(name = "max_number_students")
    private Integer maxStudents;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Enrollment> inscripciones = new ArrayList<>();


    //Getters y Setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public List<Enrollment> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Enrollment> inscripciones) {
        this.inscripciones = inscripciones;
    }

    
}
