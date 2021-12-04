package com.agence.hotelproject.services;

import com.agence.hotelproject.entities.ClientEntity;
import com.agence.hotelproject.entities.HotelEntity;
import com.agence.hotelproject.entities.ResaEntity;
import com.agence.hotelproject.repositories.ClientRepository;
import com.agence.hotelproject.repositories.HotelRepository;
import com.agence.hotelproject.repositories.ResaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.InvalidObjectException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class ResaService {
    @Autowired
    private ResaRepository resaRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ClientRepository clientRepository;


    public Iterable<ResaEntity> findAll() {
        return resaRepository.findAll();
    }

    public Iterable<ResaEntity> findAll(String search) {
        if (search != null && search.length() > 0) {
            return resaRepository.findByClient_NomCompletContainsOrHotel_NomContains(search, search);
        }
        return resaRepository.findAll();
    }

    public ResaEntity findResa(int id) {
        return resaRepository.findById(id).get();
    }

    // check de la reservation
    private void checkResa(ResaEntity resa) throws InvalidObjectException {

        Date currentDate = Date.valueOf(LocalDate.now());
        // check date debut  de la reservation
        if (resa.getDatedeb().before(currentDate)) {
            throw new InvalidObjectException("date début invalide");
        }
// check date fin de la reservation
        if (resa.getDatefin().before(currentDate) || resa.getDatefin().before(resa.getDatedeb())) {
            throw new InvalidObjectException("date fin invalide");
        }
// check numero de chambre
        if (resa.getNumChambre() > 100) {
            throw new InvalidObjectException("numero du chambre  invalide");
        }


// check hotel
        HotelEntity hotel = hotelRepository.findById(resa.getHotel().getId()).orElseGet(null);
        if (hotel == null) {
            throw new InvalidObjectException("Hotel invalide");
        }
// check client
        ClientEntity client = clientRepository.findById(resa.getHotel().getId()).orElseGet(null);
        if (client == null) {
            throw new InvalidObjectException("Client invalide");
        }
    }

        public void addResa(ResaEntity resa) throws InvalidObjectException {
            checkResa(resa);
            resaRepository.save(resa);
        }

        public void editResa ( int id, ResaEntity resa) throws InvalidObjectException {
            checkResa(resa);

            try {
                ResaEntity resaExistant = resaRepository.findById(id).get();

                resaExistant.setClient(resa.getClient());
                resaExistant.setHotel(resa.getHotel());
                resaExistant.setDatedeb(resa.getDatedeb());
                resaExistant.setDatefin(resa.getDatefin());
                resaExistant.setNumChambre(resa.getNumChambre());

                resaRepository.save(resaExistant);

            } catch (NoSuchElementException e) {
                throw e;
            }
        }

    public void delete(int id) {
        resaRepository.deleteById(id);
    }
    }
