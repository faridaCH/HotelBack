package com.agence.hotelproject.api;

import com.agence.hotelproject.entities.AdminEntity;
import com.agence.hotelproject.services.AdminService ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminAPIController {



 // autre façon d'injecter les dépendances
    AdminService  adminService ;
    public AdminAPIController(AdminService  adminService  ){
        this.adminService  = adminService ;
    }


    @GetMapping(value="" , produces = "application/json")
    public Iterable<AdminEntity> getAll(HttpServletRequest request ){

        return adminService .findAll();
    }

    @GetMapping(value="/" , produces = "application/json")
    public Iterable<AdminEntity> getAllByParam(HttpServletRequest request ){
       String search = request.getParameter("search");
        System.out.println( "Recherche de admin avec param = " + search );
        return adminService .findAll( search );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AdminEntity> get(@PathVariable int id) {
        try{
            AdminEntity admin = adminService .findAdmin(id);
            return ResponseEntity.ok(admin);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<Object> add( @RequestBody AdminEntity admin ){
        System.out.println( admin );
        try{
            adminService .add( admin );

            // création de l'url d'accès au nouvel objet =>ex: http://localhost:8080/api/admin/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( admin.getId() ).toUri();

            return ResponseEntity.created( uri ).body(admin);

        }catch ( InvalidObjectException e ){
            System.out.println("erreur "+e.getMessage());
           // throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
     return ResponseEntity.badRequest().body(e);
        }

    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody AdminEntity admin ){
        try{
            adminService .edit( id , admin );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Admin introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        // Check sur l'existance de la admin, si ko => 404 not found
        try{
            AdminEntity admin = adminService .findAdmin(id);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }

        // si on a un problème à ce niveau => sql exception
        try{
            adminService .delete(id);
            return ResponseEntity.ok(null);
        }catch( Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}