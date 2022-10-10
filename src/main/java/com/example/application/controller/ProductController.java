package com.example.application.controller;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.abc.HibernateUtils;
import com.example.application.request.AddProductRequest;
import com.example.application.request.UpdateProductRequest;
import com.example.application.response.ProductResponse;
import com.example.application.response.ErrorResponse;
import com.example.application.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	HibernateUtils hibernate;
	ErrorResponse errresp = new ErrorResponse();
	@Autowired
	ProductService productService;
	@PostMapping("/AddProduct")
	public ResponseEntity<?> addproduct(@RequestBody AddProductRequest req) {
		try{ProductResponse resp = productService.addProduct(req);
		return new ResponseEntity<ProductResponse>(resp,HttpStatus.OK);}
		catch (IOException e) {
			return new ResponseEntity<ErrorResponse>(errresp, HttpStatus.CONFLICT);
		}
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest req) {
		if(req.getId()==null) {
			return new ResponseEntity<ErrorResponse>(errresp, HttpStatus.BAD_REQUEST);
		}
		ProductResponse resp;
		try {
			resp = productService.updateProduct(req);
			return new ResponseEntity<ProductResponse>(resp, HttpStatus.OK);
		} 
		catch (IOException e) {
			return new ResponseEntity<ErrorResponse>(errresp, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getProduct(@PathVariable() int id) {
		try{
			ProductResponse resp= productService.getProduct(id);
			return new ResponseEntity<ProductResponse>(resp, HttpStatus.OK);}
		catch(Exception e) {
			return new ResponseEntity<ErrorResponse>(errresp, HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/{category}")
	public ResponseEntity<?> getProductBycategory(@PathVariable() String category) {
		ProductResponse resp= productService.getProductByCategory(category);
		return new ResponseEntity<ProductResponse>(resp, HttpStatus.OK);
	}
}
