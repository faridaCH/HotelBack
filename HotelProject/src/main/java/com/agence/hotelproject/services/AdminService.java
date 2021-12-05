package com.agence.hotelproject.services;


import com.agence.hotelproject.entities.AdminEntity;
import com.agence.hotelproject.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
    public class AdminService {

    // injection d'independance
    @Autowired
    private AdminRepository adminRepository;
      /*
        //autre façon d'injecter les dépendances
        private AdminRepository adminRepository;

        public AdminService(AdminRepository adminRepository ){
            this.adminRepository = adminRepository;
        }
       */

    @Autowired
    private PasswordEncoder encoder;

        public Iterable<AdminEntity> findAll(  ) {
            return adminRepository.findAll();
        }

        public Iterable<AdminEntity> findAll(  String search  ) {
            if( search != null && search.length() > 0 ){
                return adminRepository.findByUsernameContains(search);
            }
            return adminRepository.findAll();
        }

        public Page<AdminEntity> findALL(Integer pageNo, Integer pageSize , String search  ) {
            Pageable paging = PageRequest.of(pageNo, pageSize);

            if( search != null && search.length() > 0 ){
                return adminRepository.findByUsernameContains(search, paging );
            }

            return adminRepository.findAll( paging );
        }

/*
    // check admin email ( a verifier )
    public static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    // check admin Telephone ( a verifier )
    public static boolean validatePhoneNumber(String phone) {
        String str = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
        return  Pattern.compile(str).matcher(phone).matches();

    }
*/
    // check  admin attributes
    private void checkAdmin( AdminEntity admin ) throws InvalidObjectException {

        // check  UserName   ----> ( attention il faut checker aussi l'unicité de user name )
        Pattern VALID_NAME=Pattern.compile("[A-Za-z]{3,25}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_NAME.matcher(admin.getUsername());
        if (!matcher.find()) {
            throw new InvalidObjectException(" UserName invalide ");
        }
        
        // check passeword
        if (admin.getPassword().length() <= 2 ) {
            throw new InvalidObjectException("Password invalide ");
        }

        // check Role
        if (admin.getRole() ==null ) {
            throw new InvalidObjectException(" Role invalide ");
        }
    }

        public AdminEntity findAdmin(int id) {
            return adminRepository.findById(id).get();
        }


    public void add(AdminEntity admin) throws InvalidObjectException {
        checkAdmin(admin);
        admin.setPassword(encoder.encode(admin.getPassword()));

        adminRepository.save(admin);
    }


        public void edit( int id , AdminEntity admin) throws InvalidObjectException , NoSuchElementException {
            checkAdmin(admin);
            try{
                AdminEntity adminExistant = adminRepository.findById(id).get();

                adminExistant.setUsername( admin.getUsername() );
                adminExistant.setRole( admin.getRole() );
                adminRepository.save( adminExistant );
                if( admin.getPassword().length() > 0 ){
                    adminExistant.setPassword( encoder.encode( admin.getPassword() ) );
                }
            }catch ( NoSuchElementException e ){
                throw e;
            }

        }

    public void delete(int id) {
        adminRepository.deleteById(id);
    }

    public void editProfil( int id , AdminEntity u) throws NoSuchElementException {
        try{
            AdminEntity adminExistant = adminRepository.findById(id).get();


            adminExistant.setUsername( u.getUsername() );
            adminExistant.setPassword( u.getPassword() );
            // ligne a mettre en commentaire ou a supprimmer le user ne doit pas modifier son role
            adminExistant.setRole( u.getRole() );

            adminRepository.save( adminExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
    }

