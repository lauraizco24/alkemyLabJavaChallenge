package ar.com.alkemylab.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ar.com.alkemylab.entities.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String dni);
    
}
