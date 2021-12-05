package com.agence.hotelproject.api;

import com.agence.hotelproject.entities.ResaEntity;
import com.agence.hotelproject.services.ResaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/resa")
public class ResaAPIController {
@Autowired
  private  ResaService resaService;


    @GetMapping(value="" , produces = "application/json")
    public Iterable<ResaEntity> getAll(HttpServletRequest request ){
        String search = request.getParameter("search");
        System.out.println( "Recherche de resa avec param = " + search );
        return resaService.findAll( search );

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResaEntity> get(@PathVariable int id) {
        try{
            ResaEntity p = resaService.findResa(id);
            return ResponseEntity.ok(p);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<ResaEntity> add(@RequestBody ResaEntity resa ){
        try{
            resaService.addResa( resa );

            // création de l'url d'accès au nouvel objet => http://localhost:8080/api/resa/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( resa.getId() ).toUri();

            return ResponseEntity.created( uri ).body(resa);

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody ResaEntity p ){
        try{
            resaService.editResa( id , p );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Resa introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) throws Exception {
        try{
            resaService.delete(id);
            return ResponseEntity.ok(null);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }

    }
}