package ar.com.alkemylab.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.alkemylab.entities.Enrollment;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {

   
}
