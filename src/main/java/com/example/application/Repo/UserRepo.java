package com.example.application.Repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Users;
@Repository
@Component
public  interface UserRepo extends JpaRepository<Users,Integer> {
    ArrayList<Users> findByemail(String email);
    List<Users> findById(int id);
}

