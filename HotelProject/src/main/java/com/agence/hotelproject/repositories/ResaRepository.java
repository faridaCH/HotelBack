package com.agence.hotelproject.repositories;

import com.agence.hotelproject.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ResaRepository extends CrudRepository<ResaEntity , Integer> {

   Iterable<ResaEntity> findByHotel_Ville(String nom );

   Iterable<ResaEntity> findByClient_NomCompletContains( String nom );

   Iterable<ResaEntity> findByClient_NomCompletContainsOrHotel_NomContains( String nom , String prenom );

    Iterable<ResaEntity> findByDatedebAndClient_NomCompletContains(Date date , String nom );



}