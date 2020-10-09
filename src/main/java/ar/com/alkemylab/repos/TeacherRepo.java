package ar.com.alkemylab.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemylab.entities.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
    
}
