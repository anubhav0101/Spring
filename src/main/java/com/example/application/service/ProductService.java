package com.example.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.application.abc.HibernateUtils;
import com.example.application.entity.Product_subcategory;
import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;
import com.example.application.request.AddProductRequest;
import com.example.application.request.SubcategoryRequest;
import com.example.application.request.UpdateProductRequest;
import com.example.application.response.ProductResponse;
import com.example.application.response.ResultResponse;

@Service
public class ProductService {

	@Autowired
	HibernateUtils hibernate;

	public ProductResponse addProduct(AddProductRequest req) throws IOException {
		ProductResponse resp = new ProductResponse();
		Products p = new Products();
		Product_subcategory psub = new Product_subcategory();
		try {
			if (hibernate.checkProduct(req.getName()) == true) {
				throw new IOException("IOException Occurred");
			}

		} catch (Exception e) {
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			hibernate.save(p);
			Subcategory subcat = new Subcategory();
			SubcategoryRequest[] sc = req.getSubcategory();
			for (int i = 0; i < sc.length; i++) {
				try {
					if (hibernate.checkSubcategorybyname(sc[i].getName()) == true)
					{
						psub.setSubcategory(hibernate.getSubcategorybyname(sc[i].getName()));
					}
				} catch (Exception e1) {
					subcat.setName(sc[i].getName());
//					hibernate.save(subcat);
					
					psub.setSubcategory(subcat);
				} finally {
					psub.setProduct(p);
					hibernate.save(psub);
				}
			}
			resp.setProductid(p.getId());
			resp.setName(req.getName());
			resp.setPrice(req.getPrice());
			resp.setDetails(req.getDetails());
			resp.setCategory(req.getCategory());
			resp.setSubcategory(req.getSubcategory());
		}
		return resp;
	}

	public ProductResponse updateProduct(UpdateProductRequest req) throws IOException {

		ProductResponse resp = new ProductResponse();
		Products p = new Products();
		Product_subcategory psub = new Product_subcategory();
		ResultResponse resresp = new ResultResponse();
		try {

			if (hibernate.checkProduct(req.getName()) == true) {
				throw new IOException("IOException Occurred");
				}

		} catch (Exception e) {
			System.out.println(e);
			p.setId(req.getId());
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			hibernate.update(p);
			Subcategory subcat = hibernate.findEntityById(psub, p.getId()).getSubcategory();
			SubcategoryRequest[] sc = req.getSubcategory();
			for (int i = 0; i < sc.length; i++) {
				try {
					if (hibernate.checkSubcategorybyname(sc[i].getName()) == true)
					{
						psub.setSubcategory(hibernate.getSubcategorybyname(sc[i].getName()));
					}
				} catch (Exception e1) {
					subcat.setName(sc[i].getName());
					hibernate.save(subcat);
					psub.setSubcategory(subcat);
				} finally {
//				System.out.println(hibernate.getPsubByproductid(hibernate.findEntityById(p,req.getId())));
//				psub.setProduct(p);
//				hibernate.update(psub);
				}
			}
			resp.setProductid(p.getId());
			resp.setName(req.getName());
			resp.setPrice(req.getPrice());
			resp.setDetails(req.getDetails());
			resp.setCategory(req.getCategory());
			resp.setSubcategory(req.getSubcategory());
		}
		return resp;
	}

	public ProductResponse getProduct(int pid) {
		ProductResponse resp = new ProductResponse();
		Products p = new Products();
		Products product = hibernate.findEntityById(p, pid);
		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());
//			resp.setSubcategory(product.getSubcategory());
		return resp;
	}

	public ProductResponse getProductByCategory(String category) {
		ProductResponse resp = new ProductResponse();
		Products product = hibernate.productByCategory(category);
		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());
//			resp.setSubcategory(product.getSubcategory());
		return resp;
	}

	public ResponseEntity<?> getProductBySearchString(String search) {

		ProductResponse resp = new ProductResponse();
		Products product = hibernate.productBystring(search);
		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());
//			resp.setSubcategory(product.getSubcategory());
		return new ResponseEntity<ProductResponse>(resp, HttpStatus.OK);
	}
}
