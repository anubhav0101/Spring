package com.example.application.response;

import java.util.List;

import com.example.application.request.Subcategoryreq;

public class AddProductresp {

	private Integer productid;
	private String name;
	private Integer price;
	private String details;
	private String category;
	private Subcategoryreq[] subcategory;
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Subcategoryreq[] getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Subcategoryreq[] subcategory) {
		this.subcategory = subcategory;
	}
	
	
}
