package com.example.MySBSCRUD.repositories;

import com.example.MySBSCRUD.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

// интерфейс в котором автореализованы спрингбутом круды для юзеров. В ручную расписывать не надо!!
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail (String email);
}
