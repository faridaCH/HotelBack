package com.agence.hotelproject.security;

import com.agence.hotelproject.entities.AdminEntity;
import com.agence.hotelproject.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check si le user existe en bd
        AdminEntity user = adminRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("No user named " + username);
        } else {
            return new AdminDetailsImpl(user);
        }
    }

}