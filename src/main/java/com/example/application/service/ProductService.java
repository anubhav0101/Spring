package com.example.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.application.dao.HibernateUtils;
import com.example.application.entity.Product_subcategory;
import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;
import com.example.application.request.AddProductReq;
import com.example.application.request.Subcategoryreq;
import com.example.application.request.UpdateProduct;
import com.example.application.response.AddProductresp;
import com.example.application.response.ResultResp;

@Service
public class ProductService {

	@Autowired
	HibernateUtils hibernate;

	public AddProductresp addProduct(AddProductReq req) throws IOException {
		AddProductresp resp = new AddProductresp();
		Products p = new Products();
		Product_subcategory psub = new Product_subcategory();
		try {
			if (hibernate.checkProduct(req.getName()) == true) {
				throw new IOException("IOException Occurred");
//				resresp.setResult("This Product already exists");
//				return new ResponseEntity<ResultResp>(resresp, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			hibernate.save(p);
			Subcategory subcat = new Subcategory();
			Subcategoryreq[] sc = req.getSubcategory();
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

	public ResponseEntity<?> updateProduct(UpdateProduct req) {

		AddProductresp resp = new AddProductresp();
		Products p = new Products();
		Product_subcategory psub = new Product_subcategory();
		ResultResp resresp = new ResultResp();
		try {

			if (hibernate.checkProduct(req.getName()) == true) {
				resresp.setResult("This Product already exists");
				return new ResponseEntity<ResultResp>(resresp, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			p.setId(req.getId());
			p.setName(req.getName());
			p.setDetails(req.getDetails());
			p.setCategory(req.getCategory());
			p.setPrice(req.getPrice());
			hibernate.update(p);
			Subcategory subcat = hibernate.findEntityById(psub, p.getId()).getSubcategory();
			Subcategoryreq[] sc = req.getSubcategory();
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
		return new ResponseEntity<AddProductresp>(resp, HttpStatus.OK);
	}

	public ResponseEntity<?> getProduct(int pid) {
		AddProductresp resp = new AddProductresp();
		Products p = new Products();

		Products product = hibernate.findEntityById(p, pid);

		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());

//			resp.setSubcategory(product.getSubcategory());
		return new ResponseEntity<AddProductresp>(resp, HttpStatus.OK);
	}

	public ResponseEntity<?> getProductByCategory(String category) {

		AddProductresp resp = new AddProductresp();
		Products product = hibernate.productByCategory(category);

		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());

//			resp.setSubcategory(product.getSubcategory());
		return new ResponseEntity<AddProductresp>(resp, HttpStatus.OK);
	}

	public ResponseEntity<?> getProductBySearchString(String search) {

		AddProductresp resp = new AddProductresp();
		Products product = hibernate.productBystring(search);
		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());

//			resp.setSubcategory(product.getSubcategory());
		return new ResponseEntity<AddProductresp>(resp, HttpStatus.OK);
	}
}
