package com.example.application.controller;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.dao.HibernateUtils;
import com.example.application.entity.Products;
import com.example.application.entity.Users;
import com.example.application.request.AddProductReq;
import com.example.application.request.Loginreq;
import com.example.application.request.UpdateProduct;
import com.example.application.response.AddProductresp;
import com.example.application.response.ResultResp;
import com.example.application.response.UpdateResp;
import com.example.application.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	HibernateUtils hibernate;
	
	@Autowired
	ProductService productService;
	@PostMapping("/AddProduct")
	public ResponseEntity<?> addproduct(@RequestBody AddProductReq req) {
		ResponseEntity<?> resp = productService.addProduct(req);
		return resp;
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody UpdateProduct req) {
		ResponseEntity<?> resp= productService.updateProduct(req);
		return resp;
	}
}
