package com.example.application.Repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Users;
@Repository
@Component
public  interface UserRepo extends JpaRepository<Users,Integer> {
    List<Users> findByEmail(String email);
    
    List<Users> findById(int id);
    Boolean existsByEmail(String email);
    default Users findEntityById(int id) {
    	return findById(id).get(0);
    }
}

