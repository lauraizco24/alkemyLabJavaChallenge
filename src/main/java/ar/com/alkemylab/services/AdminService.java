package ar.com.alkemylab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemylab.entities.Admin;
import ar.com.alkemylab.entities.Users;
import ar.com.alkemylab.models.request.RegistrationRequest;
import ar.com.alkemylab.repos.AdminRepo;
import ar.com.alkemylab.security.Crypto;
import ar.com.alkemylab.services.base.GenericService;

@Service
public class AdminService extends GenericService<Admin> {

    @Autowired
    UserService uService;

    private AdminRepo repo() {
        return (AdminRepo) repo;
    }

    public Admin createAdmin(RegistrationRequest aRequest) {
        if (AdminByDni(aRequest.dni) == null) {
            Admin adminC = new Admin();
            Users user = new Users();

            adminC.setDni(aRequest.dni);
            adminC.setName(aRequest.name);
            adminC.setLastName(aRequest.lastName);
            adminC.setFile("admin" + aRequest.dni + aRequest.lastName);
            user.setName(aRequest.name);
            user.setLastName(aRequest.lastName);
            user.setUsername(aRequest.dni.toString());
            user.setPassword(Crypto.encrypt(adminC.getFile(), aRequest.dni.toString()));
            user.setUserTypeId(aRequest.userType);
            adminC.setUser(user);
            create(adminC);

            return adminC;
        } else {
            return null;
        }

    }

    public Admin AdminByDni(Integer dni) {

        return repo().findByDni(dni);

    }
}
