package com.agence.hotelproject.repositories;

import com.agence.hotelproject.entities.AdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends PagingAndSortingRepository<AdminEntity, Integer> {


    public AdminEntity findByUsername(String search );
    public Iterable<AdminEntity> findByUsernameContains(String search );
    public Page<AdminEntity> findByUsernameContains(String search , Pageable pageable);

    public Page<AdminEntity> findAll(Pageable pageable);

    @Query( value="SELECT * FROM admin WHERE ( username = ?1) AND  password = ?2", nativeQuery=true )
    public AdminEntity findByCustom(String username , String pass); // , Date rdvDate

}