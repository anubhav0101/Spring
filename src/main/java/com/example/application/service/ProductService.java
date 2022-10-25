package com.example.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Repo.ProductRepo;
import com.example.application.Repo.SubcategoryRepo;
import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;
import com.example.application.exception.NoSuchProductExistsExceptio;
import com.example.application.exception.ProductAlreadyExistsException;
import com.example.application.request.AddProductRequest;
import com.example.application.request.UpdateProductRequest;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	@Autowired
	SubcategoryRepo subcategoryRepo;

	public Products addProduct(AddProductRequest req) throws Exception {
		Products p = new Products();
		if (productRepo.existsByName(req.getName())) {
			throw new ProductAlreadyExistsException();
		} else {
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			Set<Subcategory> sc = new HashSet<>();
			for (int i = 0; i < req.getSubCategory().size(); i++) {
				Subcategory subcat = new Subcategory();
				if (subcategoryRepo.existsByName(req.getSubCategory().get(i).getName())) {
					sc.addAll(subcategoryRepo.findSubcatByName(req.getSubCategory().get(i).getName()));
					p.setSubcategory(sc);
				} else {
					subcat.setName(req.getSubCategory().get(i).getName());
					subcategoryRepo.save(subcat);
					sc.add(subcat);
					p.setSubcategory(sc);
				}
				productRepo.save(p);
			}
		}
		return p;
	}

	public Products updateProduct(UpdateProductRequest req) throws Exception {

		Products p = new Products();
		if (productRepo.existsByName(req.getName())) {
			throw new ProductAlreadyExistsException(); 
		}

		else {
			p.setId(req.getId());
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			Set<Subcategory> sc = new HashSet<>();
			for (int i = 0; i < req.getSubCategory().size(); i++) {
				Subcategory subcat = new Subcategory();
				if (subcategoryRepo.existsByName(req.getSubCategory().get(i).getName())) {
					sc.addAll(subcategoryRepo.findSubcatByName(req.getSubCategory().get(i).getName()));
					p.setSubcategory(sc);
				} else {
					subcat.setName(req.getSubCategory().get(i).getName());
					subcategoryRepo.saveAndFlush(subcat);
					sc.add(subcat);
					p.setSubcategory(sc);
				}
				productRepo.saveAndFlush(p);
			}
		}
		return p;
	}

	public Products getProduct(int pid) {
		Products product = productRepo.findEntityById(pid);
		return product;
	}

	public List<Products> getProductByCategory(String category) throws Exception {
		if(productRepo.existsByCategory(category)) {
		List<Products> product = productRepo.findByCategory(category);
		return product;}
		else
			throw new NoSuchProductExistsExceptio(); 
	}
	public List<Products> searchString(String searchString) {

		List<Products> searchcat = productRepo.findByCategoryContaining(searchString);
		List<Products> searchname = productRepo.findByNameContaining(searchString);
		List<Products> searchdetails = productRepo.findByDetailsContaining(searchString);
		List<Products> merge = new ArrayList<Products>();
		merge.addAll(searchcat);
        merge.addAll(searchname);
        merge.addAll(searchdetails);
		List<Products>product=merge.stream().distinct().collect(Collectors.toList());
		return product;
	}
}
