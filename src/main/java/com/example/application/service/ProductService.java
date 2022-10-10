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
import com.example.application.request.AddProductReq;
import com.example.application.request.Subcategoryreq;
import com.example.application.request.UpdateProduct;
import com.example.application.response.Productresp;
import com.example.application.response.ResultResp;

@Service
public class ProductService {

	@Autowired
	HibernateUtils hibernate;

	public Productresp addProduct(AddProductReq req) throws IOException {
		Productresp resp = new Productresp();
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
			Subcategoryreq[] sc = req.getSubcategory();
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

	public Productresp updateProduct(UpdateProduct req) throws IOException {

		Productresp resp = new Productresp();
		Products p = new Products();
		Product_subcategory psub = new Product_subcategory();
		ResultResp resresp = new ResultResp();
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
		return resp;
	}

	public Productresp getProduct(int pid) {
		Productresp resp = new Productresp();
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

	public Productresp getProductByCategory(String category) {
		Productresp resp = new Productresp();
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

		Productresp resp = new Productresp();
		Products product = hibernate.productBystring(search);
		resp.setProductid(product.getId());
		resp.setName(product.getName());
		resp.setPrice(product.getPrice());
		resp.setDetails(product.getDetails());
		resp.setCategory(product.getCategory());
//			resp.setSubcategory(product.getSubcategory());
		return new ResponseEntity<Productresp>(resp, HttpStatus.OK);
	}
}
