package com.example.MySBSCRUD.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


// Энтити пользователи для отображения в бд
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @Size(min=5, message = "Не меньше 5 знаков")
    private String name;

    @Column(nullable = false, unique = true)
    @Size(min=5, message = "Не меньше 5 знаков")
    private String email;

    @Column(nullable = false)
    @Size(min=5, message = "Не меньше 5 знаков")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public List<Role> getRoles()
    {
        return roles;
    }
    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public int getId() { return id;}
    public void setId (int id){ this.id=id;}

    public String getEmail (String email){ return email;}
    public void setEmail (String email){ this.email=email;}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() { return password;}
    public void setPassword(String password){ this.password=password;}

    @Override
    public String getUsername() { return name;}
    public void setUsername (String name){ this.name=name;}

    @Override
    public boolean isAccountNonExpired() { return true;}

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
