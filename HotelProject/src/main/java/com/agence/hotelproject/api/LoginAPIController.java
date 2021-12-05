package com.agence.hotelproject.api;

import com.agence.hotelproject.entities.AdminEntity;
import com.agence.hotelproject.repositories.AdminRepository;
import com.agence.hotelproject.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@RestController
public class LoginAPIController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AdminService adminService;

    @PostMapping( value = "/api/login" ,  consumes = "application/json" ,  produces = "application/json")
    public ResponseEntity<AdminEntity> get(@RequestBody  AdminEntity u ) {

        AdminEntity admin = adminRepository.findByUsername( u.getUsername());

        if(admin == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println( "encoded pass : " + u.getPassword() );
            System.out.println( "pass en bd : " + admin.getPassword() );

            // admin exists
            if( encoder.matches( u.getPassword() , admin.getPassword() ) ){
                String encoding1 = Base64.getEncoder().encodeToString((u.getUsername()+":"+u.getPassword()).getBytes());
                admin.setPassword(encoding1);

                return ResponseEntity.ok(admin);
            }
            return ResponseEntity.badRequest().build();
        }

    }


    @PutMapping( value = "/api/profil/{id}" ,  produces = "application/json")
    public ResponseEntity<AdminEntity> editProfil(@PathVariable int id ,  @RequestParam(value = "photoadmin" , required = false) MultipartFile file , HttpServletRequest request ) throws IOException {
        // Récupération des paramètres envoyés en POST
        String titi = request.getParameter("name");
        // not used =>  a supprimer car un user ne doit pas modifier sont role
        String role= request.getParameter("roles");
        String password = request.getParameter("password");;
        String username = request.getParameter("username");

        // Préparation de l'entité à sauvegarderpassword
        AdminEntity admin = new AdminEntity( username, password,role);
        admin.setId( id );

        // Enregistrement en utilisant la couche service qui gère déjà nos contraintes
        try{
            adminService.editProfil( id, admin );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        return ResponseEntity.ok(admin);
    }
}