package ar.com.alkemylab.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.alkemylab.entities.Subject;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {
    
    boolean existsBySubjectName(String name);

    List<Subject> findByOrderBySubjectNameAsc();
    
}
