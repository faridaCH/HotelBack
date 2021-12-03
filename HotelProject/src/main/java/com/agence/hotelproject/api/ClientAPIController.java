package com.agence.hotelproject.api;

import com.agence.hotelproject.entities.ClientEntity;
import com.agence.hotelproject.services.ClientService;
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
@RequestMapping("/api/client")
public class ClientAPIController {



 // autre façon d'injecter les dépendances
    ClientService clientService;
    public ClientAPIController( ClientService clientService ){
        this.clientService = clientService;
    }


    @GetMapping(value="" , produces = "application/json")
    public Iterable<ClientEntity> getAll(HttpServletRequest request ){

        return clientService.findAll();
    }

    @GetMapping(value="/" , produces = "application/json")
    public Iterable<ClientEntity> getAllByParam(HttpServletRequest request ){
       String search = request.getParameter("search");
        System.out.println( "Recherche de client avec param = " + search );
        return clientService.findAll( search );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClientEntity> get(@PathVariable int id) {
        try{
            ClientEntity client = clientService.findClient(id);
            return ResponseEntity.ok(client);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<ClientEntity> add( @RequestBody ClientEntity client ){
        System.out.println( client );
        try{
            clientService.add( client );

            // création de l'url d'accès au nouvel objet =>ex: http://localhost:8080/api/client/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( client.getId() ).toUri();

            return ResponseEntity.created( uri ).body(client);

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }

    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody ClientEntity client ){
        try{
            clientService.edit( id , client );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Client introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        // Check sur l'existance de la client, si ko => 404 not found
        try{
            ClientEntity client = clientService.findClient(id);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }

        // si on a un problème à ce niveau => sql exception
        try{
            clientService.delete(id);
            return ResponseEntity.ok(null);
        }catch( Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}