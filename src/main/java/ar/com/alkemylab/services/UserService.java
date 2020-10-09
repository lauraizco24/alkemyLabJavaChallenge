package ar.com.alkemylab.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ar.com.alkemylab.entities.Admin;
import ar.com.alkemylab.entities.Student;
import ar.com.alkemylab.entities.Users;
import ar.com.alkemylab.entities.Users.UserTypeEnum;
import ar.com.alkemylab.repos.UserRepo;
import ar.com.alkemylab.security.Crypto;
import ar.com.alkemylab.services.base.GenericService;

@Service
public class UserService extends GenericService<Users> {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;

    private UserRepo repo() {
        return (UserRepo) repo;
    }

   public Users findByUsername(String username){
       return repo().findByUsername(username);
   }
   public Users login(String username, String password) {

    /**
     * Metodo IniciarSesion recibe usuario y contraseña validar usuario y contraseña
     */

    Users u = findByUsername(username);

    if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getUsername()))) {

        throw new BadCredentialsException("Usuario o contraseña invalida");
    }

    return u;
}
   
public Users createUser(UserTypeEnum userType, String name,  String lastName,
            Integer dni) {

        if(findByUsername(dni.toString())==null){

            Users user = new Users();
            user.setName(name);
            user.setLastName(lastName);
            user.setUserTypeId(userType);
            user.setUsername(dni.toString());
            user.setPassword(Crypto.encrypt(dni + lastName, dni.toString()));
            
    
            switch (userType) {
                case ADMIN:
    
                    Admin admin = new Admin();
                    admin.setName(name);
                    admin.setDni(dni);
                    admin.setLastName(lastName);
                    admin.setFile(dni + lastName);
                    admin.setUser(user);
                    adminService.create(admin);
                    break;
    
                case STUDENT:
                    Student student = new Student();
                    student.setDni(dni);
                    student.setName(name);
                    student.setLastName(lastName);
                    student.setFile( dni + lastName);
                    student.setUser(user);
                    studentService.create(student);
                    
                    break;
    
                default:
                    break;
            }
            return user;

        }
        else{
            return null;
        }

       
    }

    public Map<String, Object> getUserClaims(Users user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userType", user.getUserTypeId());

        if (user.getEntityId() != null)
            claims.put("entityId", user.getEntityId());

        return claims;
    }

    public UserDetails getUserAsUserDetail(Users user) {
        UserDetails uDetails;

        uDetails = new User(user.getUsername(), user.getPassword(),
                getAuthorities(user));

        return uDetails;
    }

    Set<? extends GrantedAuthority> getAuthorities(Users user) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        UserTypeEnum userType = user.getUserTypeId();

        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

        if (user.getEntityId() != null)
            authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + user.getEntityId()));
        return authorities;
    }

}


