package com.example.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;
import com.example.application.exception.ProductAlreadyExistsException;
import com.example.application.request.AddProductRequest;
import com.example.application.request.SubcategoryRequest;
import com.example.application.request.UpdateProductRequest;
import com.example.application.response.ResultResponse;
import com.example.application.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;
	ResultResponse response=new ResultResponse();

	@PostMapping("/AddProduct")
	public ResponseEntity<?> addproduct(@RequestBody AddProductRequest req) {
		try {
			Products p = productService.addProduct(req);
			return new ResponseEntity<Products>(p, HttpStatus.OK);
		} catch (ProductAlreadyExistsException e) {
			response.setResult("Product Already exists");
			return new ResponseEntity<ResultResponse>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest req) {
		if (req.getId() == null) {
			return new ResponseEntity<ResultResponse>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Products p = productService.updateProduct(req);
			return new ResponseEntity<Products>(p, HttpStatus.OK);
		}  catch (ProductAlreadyExistsException e) {
			response.setResult("Product Already exists");
			return new ResponseEntity<ResultResponse>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
//
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getProduct(@PathVariable() int id) {
		try {
			Products products = productService.getProduct(id);
			return new ResponseEntity<Products>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(response, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{category}")
	public ResponseEntity<?> getProductBycategory(@PathVariable() String category) {
		
		try {
			List<Products> products = productService.getProductByCategory(category);
			return new ResponseEntity<List<Products>>(products, HttpStatus.OK);
		}
		catch (ProductAlreadyExistsException e) {
			response.setResult("Such Product doesnot exists");
			return new ResponseEntity<ResultResponse>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(response, HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/search/{searchString}")
	public ResponseEntity<?> searchString(@PathVariable() String searchString) {
		 List<Products> products= productService.searchString(searchString);
		 return new ResponseEntity<List<Products>>(products,HttpStatus.OK);
	}
	
//	@PostMapping("/{category}/getFilteredProducts")
//	public ResponseEntity<?> filter(@PathVariable() String category,@RequestBody FilterReq req) {
//		 List<Products> products= productService.searchString(searchString);
//		 return new ResponseEntity<List<Products>>(products,HttpStatus.OK);
//	}
}
