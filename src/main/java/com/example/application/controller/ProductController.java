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
import com.example.application.request.AddProductReq;
import com.example.application.request.UpdateProduct;
import com.example.application.response.Productresp;
import com.example.application.response.ErrorResp;
import com.example.application.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	HibernateUtils hibernate;
	ErrorResp errresp = new ErrorResp();
	@Autowired
	ProductService productService;
	@PostMapping("/AddProduct")
	public ResponseEntity<?> addproduct(@RequestBody AddProductReq req) {
		try{Productresp resp = productService.addProduct(req);
		return new ResponseEntity<Productresp>(resp,HttpStatus.OK);}
		catch (IOException e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.CONFLICT);
		}
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody UpdateProduct req) {
		if(req.getId()==null) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.BAD_REQUEST);
		}
		Productresp resp;
		try {
			resp = productService.updateProduct(req);
			return new ResponseEntity<Productresp>(resp, HttpStatus.OK);
		} 
		catch (IOException e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getProduct(@PathVariable() int id) {
		try{
			Productresp resp= productService.getProduct(id);
			return new ResponseEntity<Productresp>(resp, HttpStatus.OK);}
		catch(Exception e) {
			return new ResponseEntity<ErrorResp>(errresp, HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/{category}")
	public ResponseEntity<?> getProductBycategory(@PathVariable() String category) {
		Productresp resp= productService.getProductByCategory(category);
		return new ResponseEntity<Productresp>(resp, HttpStatus.OK);
	}
}
