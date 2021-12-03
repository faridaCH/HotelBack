package com.agence.hotelproject.api;

import com.agence.hotelproject.entities.HotelEntity;
import com.agence.hotelproject.services.HotelService;
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
@RequestMapping("/api/hotel")
public class HotelAPIController {



 // autre façon d'injecter les dépendances
    HotelService hotelService;
    public HotelAPIController(HotelService hotelService ){
        this.hotelService = hotelService;
    }


    @GetMapping(value="" , produces = "application/json")
    public Iterable<HotelEntity> getAll(HttpServletRequest request ){

        return hotelService.findAll();
    }

    @GetMapping(value="/" , produces = "application/json")
    public Iterable<HotelEntity> getAllByParam(HttpServletRequest request ){
       String search = request.getParameter("search");
        System.out.println( "Recherche de hotel avec param = " + search );
        return hotelService.findAll( search );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<HotelEntity> get(@PathVariable int id) {
        try{
            HotelEntity hotel = hotelService.findHotel(id);
            return ResponseEntity.ok(hotel);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<HotelEntity> add( @RequestBody HotelEntity hotel ){
        System.out.println( hotel );
        try{
            hotelService.add( hotel );

            // création de l'url d'accès au nouvel objet =>ex: http://localhost:8080/api/hotel/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( hotel.getId() ).toUri();

            return ResponseEntity.created( uri ).body(hotel);

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }

    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody HotelEntity hotel ){
        try{
            hotelService.edit( id , hotel );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Hotel introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        // Check sur l'existance de la hotel, si ko => 404 not found
        try{
            HotelEntity hotel = hotelService.findHotel(id);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }

        // si on a un problème à ce niveau => sql exception
        try{
            hotelService.delete(id);
            return ResponseEntity.ok(null);
        }catch( Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}