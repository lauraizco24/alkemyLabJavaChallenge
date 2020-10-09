package ar.com.alkemylab.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemylab.entities.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

    Admin findByDni(Integer dni);
    
}
