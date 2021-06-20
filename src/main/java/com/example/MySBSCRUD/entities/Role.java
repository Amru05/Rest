package com.example.MySBSCRUD.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

// Энтити роли для отображения в бд
@Entity
@Table(name = "Roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column (unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public List<User> getUsers(){ return users;}
    public void setUsers(List<User> users){ this.users=users;}

    public int getId(){ return id;}
    public void setId(int id){ this.id=id;}

    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}

    @Override
    public String getAuthority() {
        return getName();
    }
}
