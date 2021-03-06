package com.agence.hotelproject.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin", schema = "agencevoyage", catalog = "")
public class AdminEntity {
    private int id;
    private String username;
    private String password;
    private String role;

    public AdminEntity() {

    }
    public AdminEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AdminEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return id == that.id && password == that.password && Objects.equals(username, that.username) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }
}
