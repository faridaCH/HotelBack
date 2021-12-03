package com.agence.hotelproject.repositories;

import com.agence.hotelproject.entities.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends PagingAndSortingRepository<HotelEntity, Integer> {


    public List<HotelEntity> findByNomContains(String nom );
    public Page<HotelEntity> findByNomContainsOrVilleContains(String nom ,String ville ,Pageable pageable);

    public Page<HotelEntity> findAll(Pageable pageable);

}