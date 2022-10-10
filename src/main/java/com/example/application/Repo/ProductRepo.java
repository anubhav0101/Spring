package com.example.application.Repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Products;

@Repository
@Component
public  interface ProductRepo extends JpaRepository<Products,Integer> {
    ArrayList<Products> findByName(String email);
    List<Products> findById(int id);
}

