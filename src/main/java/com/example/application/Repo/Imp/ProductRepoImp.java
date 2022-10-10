//package com.example.application.Repo.Imp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.example.application.Repo.AddressRepo;
//import com.example.application.Repo.ProductRepo;
//import com.example.application.Repo.UserRepo;
//import com.example.application.entity.Address;
//import com.example.application.entity.Products;
//import com.example.application.entity.Users;
//
//@Component
//public class ProductRepoImp {
//	@Autowired
//	ProductRepo productR;
//
//	public boolean checkModel(String s) {
//		ArrayList<Products> u = productR.findByName(s);
//		Integer size = u.size();
//		if (size > 0)
//			return false;
//		else
//			return true;
//	}
//
//	public List<Products> findByName(String s) {
//		return productR.findByName(s);
//	}
//
//	public void addProduct(Products u) {
//		productR.save(u);
//	}
//
//	public void updateProduct(Products u) {
//		productR.saveAndFlush(u);
//	}
//
//	public Users findUserByEmail(String s) {
//		Users u = findByemail(s).get(0);
//		return u;
//	}
//
//	public void delete(int id) {
//		userR.deleteById(id);
//	}
//
//	public Users findEntityById(int id) {
//
//		List<Users> u = userR.findById(id);
//		return u.get(0);
//	}
//}
