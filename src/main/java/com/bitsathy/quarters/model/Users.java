package com.bitsathy.quarters.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    String username;
    String name;
    @OneToOne(cascade = CascadeType.ALL)
    Faculty details;
    String password;
    @ManyToMany 
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
}
