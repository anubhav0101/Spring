package com.example.application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Address;
import com.example.application.entity.Users;
@Repository
@Component
public  interface AddressRepo extends JpaRepository<Address,Integer> {
    List<Address> findById(int id);
}
