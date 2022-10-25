package com.example.application.Repo;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;

@Repository
@Component
public  interface ProductRepo extends JpaRepository<Products,Integer> {
    List<Products> findByName(String name);
    List<Products> findByCategory(String category);
    Boolean existsByCategory(String category);
    List<Products> findByCategoryContaining(String searchString);
    List<Products> findByNameContaining(String searchString);
    List<Products> findByDetailsContaining(String searchString);
    Boolean existsByName(String name);
    List<Products> findById(int id);
    default Products findEntityById(int id) {
    	return findById(id).get(0);
    }
    default List<Products> findListById(int id) {
    	return findById(id);
    }
    
//    @Query("SELECT p FROM product p WHERE " +
//            "p.category LIKE CONCAT('%',:searchString, '%')" +
//            "Or p.details LIKE CONCAT('%', :searchString, '%')"+
//            "Or p.name LIKE CONCAT('%', :searchString, '%')"
//            )
//    List<Products> searchProducts(String searchString);
}

