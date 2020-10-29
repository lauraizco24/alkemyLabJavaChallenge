package ar.com.alkemylab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemylab.entities.Admin;

import ar.com.alkemylab.models.request.RegistrationRequest;
import ar.com.alkemylab.models.responses.GenericResponse;
import ar.com.alkemylab.models.responses.RegisterResponse;
import ar.com.alkemylab.services.AdminService;

@RestController
public class AdminController {
    
    @Autowired
    AdminService adminService;

    @CrossOrigin("*")
    @PostMapping("/admin")
    public ResponseEntity<RegisterResponse> createAdmin(@RequestBody RegistrationRequest aRequest) {

        RegisterResponse r = new RegisterResponse();
        Admin createdAdmin = new Admin();
        createdAdmin = adminService.createAdmin(aRequest);
        if (createdAdmin == null) {
            return ResponseEntity.badRequest().build();
        } else {

           r.message= "El Administrador fue registrado exitosamente";
           r.password = createdAdmin.getFile();
           r.userName = createdAdmin.getDni();
           r.id = createdAdmin.getId();

            return ResponseEntity.ok(r);
        }
    }
    @CrossOrigin("*")
    @GetMapping("/admins")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    public ResponseEntity<List<Admin>> getAdmins() {
        List<Admin> allAdmins = adminService.listAll();
        if (allAdmins == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(allAdmins);

    }
    @CrossOrigin("*")
    @GetMapping("/admins/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<Admin> findAdminById(@PathVariable Integer id) {
        Admin admin = adminService.findById(id);
        if (admin == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(admin);
    }

}
