package com.example.application.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class UpdateProductRequest {
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String name;
	private String details;
	private String category;
	private Integer price;
	private SubcategoryRequest[] subcategory;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public SubcategoryRequest[] getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubcategoryRequest[] subcategory) {
		this.subcategory = subcategory;
	}
	

}
