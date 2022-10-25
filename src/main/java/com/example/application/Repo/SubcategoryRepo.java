package com.example.application.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.example.application.entity.Subcategory;

@Repository
@Component
public interface SubcategoryRepo extends JpaRepository<Subcategory, Integer> {
	Subcategory findByName(String name);
	List<Subcategory> findSubcatByName(String name);
	Boolean existsByName(String name);
	List<Subcategory> findById(int id);
	default Subcategory findEntityById(int id) {
		return findById(id).get(0);
	}
	List<Subcategory>findByNameContaining(String name);
}
