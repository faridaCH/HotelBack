package com.agence.hotelproject.services;


import com.agence.hotelproject.entities.ClientEntity;
import com.agence.hotelproject.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
    public class ClientService  {
        
        //autre façon d'injecter les dépendances
        private ClientRepository clientRepository;

        public ClientService ( ClientRepository clientRepository ){
            this.clientRepository = clientRepository;
        }

        public Iterable<ClientEntity> findAll(  ) {
            return clientRepository.findAll();
        }

        public Iterable<ClientEntity> findAll(  String search  ) {
            if( search != null && search.length() > 0 ){
                return clientRepository.findByNomCompletContains(search);
            }
            return clientRepository.findAll();
        }
/*
        public Page<ClientEntity> findALL(Integer pageNo, Integer pageSize , String search  ) {
            Pageable paging = PageRequest.of(pageNo, pageSize);

            if( search != null && search.length() > 0 ){
                return clientRepository.findByNomCompletContains(search, paging );
            }

            return clientRepository.findAll( paging );
        }

 */

    // check client email ( a verifier )
    public static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    // check client Telephone ( a verifier )
    public static boolean validatePhoneNumber(String phone) {
        String str = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
        return  Pattern.compile(str).matcher(phone).matches();

    }

    // check  client attributes
    private void checkClient( ClientEntity client ) throws InvalidObjectException {

        // check  Nom complet
        Pattern VALID_NAME=Pattern.compile("[A-Za-z]{2,25}"+" "+ "[A-Za-z]{2,25}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_NAME.matcher(client.getNomComplet());
        // return matcher.find();
        if (!matcher.find()) {
            throw new InvalidObjectException(" nom complet invalide ");
        }

        // check Telephone
        if ( client.getTelephone()!= null && validatePhoneNumber(client.getTelephone())==false) {
            throw new InvalidObjectException(" Telephone invalide ");
        }

        // check Email
        if (client.getEmail().length() <= 5 || !validateEmail(client.getEmail())) {
            throw new InvalidObjectException("Email invalide ");
        }

        // check Adresse
        if (client.getAdresse().length() < 5 ) {
            throw new InvalidObjectException(" Adresse invalide ");
        }


    }

        public ClientEntity findClient(int id) {
            return clientRepository.findById(id).get();
        }
        


        public void add( ClientEntity client ) throws InvalidObjectException {
            checkClient(client);
            clientRepository.save(client);
        }


        public void edit( int id , ClientEntity client) throws InvalidObjectException , NoSuchElementException {
            checkClient(client);
            try{
                ClientEntity vExistante = clientRepository.findById(id).get();

                vExistante.setNomComplet( client.getNomComplet() );
                vExistante.setTelephone( client.getTelephone() );
                vExistante.setEmail( client.getEmail() );
                vExistante.setAdresse( client.getAdresse() );
                clientRepository.save( vExistante );

            }catch ( NoSuchElementException e ){
                throw e;
            }

        }

    public void delete(int id) {
        clientRepository.deleteById(id);
    }
    }

