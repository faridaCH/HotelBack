package com.agence.hotelproject.repositories;

import com.agence.hotelproject.entities.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, Integer> {


        public List<ClientEntity> findByNomCompletContains(String search );
        public Page<ClientEntity> findByNomCompletContains(String search , Pageable pageable);

        public Page<ClientEntity> findAll(Pageable pageable);

    }