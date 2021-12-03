package com.agence.hotelproject.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hotel", schema = "agencevoyage", catalog = "")
public class HotelEntity {
    private int id;
    private String nom;
    private Etoile etoiles;
    private String adresse;
    private String telephone;
    private String email;
    private String ville;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom", nullable = false)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "etoiles")
    @Enumerated(EnumType.STRING)
    public Etoile getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(Etoile etoiles) {
        this.etoiles = etoiles;
    }

    @Basic
    @Column(name = "adresse")
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "ville")
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelEntity that = (HotelEntity) o;
        return id == that.id && telephone == that.telephone && email == that.email && ville == that.ville && Objects.equals(nom, that.nom) && Objects.equals(etoiles, that.etoiles) && Objects.equals(adresse, that.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, etoiles, adresse, telephone, email, ville);
    }
}
