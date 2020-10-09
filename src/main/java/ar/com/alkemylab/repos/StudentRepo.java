package ar.com.alkemylab.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemylab.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    
    Student findByDni(Integer dni);
}
