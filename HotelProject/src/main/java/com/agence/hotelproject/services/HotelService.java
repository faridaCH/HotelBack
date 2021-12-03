package com.agence.hotelproject.services;


import com.agence.hotelproject.entities.HotelEntity;
import com.agence.hotelproject.repositories.HotelRepository;
import com.agence.hotelproject.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
    public class HotelService {

    //injection d'independance
    @Autowired
    private HotelRepository hotelRepository ;
/*
        //autre façon d'injecter les dépendances
        private HotelRepository hotelRepository ;

        public HotelService(HotelRepository hotelRepository  ){
            this.hotelRepository  = hotelRepository ;
        }
 */

        public Iterable<HotelEntity> findAll(  ) {
            return hotelRepository .findAll();
        }

        public Iterable<HotelEntity> findAll(  String search  ) {
            if( search != null && search.length() > 0 ){
                return hotelRepository .findByNomContains(search);
            }
            return hotelRepository .findAll();
        }
        public Page<HotelEntity> findALL(Integer pageNo, Integer pageSize , String search  ) {
            Pageable paging = PageRequest.of(pageNo, pageSize);

            if( search != null && search.length() > 0 ){
                return hotelRepository .findByNomContainsOrVilleContains(search, search , paging );
            }

            return hotelRepository .findAll( paging );
        }



    // check hotel email ( a verifier )
    public static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    // check hotel Telephone ( a verifier )
    public static boolean validatePhoneNumber(String phone) {
        String str = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
        return  Pattern.compile(str).matcher(phone).matches();

    }

    // check  hotel attributes
    private void checkHotel( HotelEntity hotel ) throws InvalidObjectException {

        // check  Nom
        Pattern VALID_NAME=Pattern.compile("[A-Za-z]{2,25}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_NAME.matcher(hotel.getNom());
        // return matcher.find();
        if (!matcher.find()) {
            throw new InvalidObjectException(" nom complet invalide ");
        }

        // check Telephone
        if ( hotel.getTelephone() == null || !(validatePhoneNumber(hotel.getTelephone())) == false) {
            throw new InvalidObjectException(" Telephone invalide ");
        }

        // check Email
        if (hotel.getEmail().length() <= 5 || !validateEmail(hotel.getEmail())) {
            throw new InvalidObjectException("Email invalide ");
        }

        // check Adresse
        if (hotel.getAdresse().length() < 5 ) {
            throw new InvalidObjectException(" Adresse invalide ");
        }
        // check ville
        if (hotel.getVille().length() < 2 ) {
            throw new InvalidObjectException(" Ville invalide ");
        }


    }

        public HotelEntity findHotel(int id) {
            return hotelRepository .findById(id).get();
        }
        


        public void add( HotelEntity hotel ) throws InvalidObjectException {
            checkHotel(hotel);
            hotelRepository .save(hotel);
        }


        public void edit( int id , HotelEntity hotel) throws InvalidObjectException , NoSuchElementException {
            checkHotel(hotel);
            try{
                HotelEntity hotelExistant = hotelRepository .findById(id).get();

                hotelExistant.setNom( hotel.getNom() );
                hotelExistant.setEtoiles( hotel.getEtoiles() );
                hotelExistant.setTelephone( hotel.getTelephone() );
                hotelExistant.setEmail( hotel.getEmail() );
                hotelExistant.setAdresse( hotel.getAdresse() );
                hotelExistant.setVille( hotel.getVille() );
                hotelRepository .save( hotelExistant );

            }catch ( NoSuchElementException e ){
                throw e;
            }

        }

    public void delete(int id) {
        hotelRepository .deleteById(id);
    }
    }

