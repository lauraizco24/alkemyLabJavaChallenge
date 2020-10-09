package ar.com.alkemylab.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemylab.entities.Users;
import ar.com.alkemylab.models.request.LoginRequest;
import ar.com.alkemylab.models.request.RegistrationRequest;
import ar.com.alkemylab.models.responses.LoginResponse;
import ar.com.alkemylab.models.responses.RegisterResponse;
import ar.com.alkemylab.security.jwt.JWTTokenUtil;
import ar.com.alkemylab.services.JWTUserDetailsService;
import ar.com.alkemylab.services.UserService;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;


    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponse> postRegisterUser(@RequestBody RegistrationRequest req) {
        RegisterResponse r = new RegisterResponse();

        // aca creamos la persona y el usuario a traves del service.

        Users user = userService.createUser(req.userType, req.name, req.lastName, req.dni);

        r.message="Te registraste exitosamente";
        r.id = user.getEntityId();
        r.userName = req.dni;
        r.password = user.getUsername()+user.getLastName(); // <-- AQUI ponemos el numerito de id para darle a front!

        return ResponseEntity.ok(r);

    }

    @PostMapping("/auth/login") // probando nuestro login
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        Users loggedUser = userService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = userService.getUserAsUserDetail(loggedUser);
        Map<String, Object> claims = userService.getUserClaims(loggedUser);

        String token = jwtTokenUtil.generateToken(userDetails, claims);


        LoginResponse r = new LoginResponse();
        r.id = loggedUser.getUserId();
        r.username = authenticationRequest.username;
        r.token = token;

        return ResponseEntity.ok(r);

    }

}
