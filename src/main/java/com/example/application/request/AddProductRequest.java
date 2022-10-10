package com.example.application.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AddProductRequest {

	private String name;
	private String details;
	private String category;
	private Integer price;
	private SubcategoryRequest[] subcategory;
	
	
	public SubcategoryRequest[] getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubcategoryRequest[] subcategory) {
		this.subcategory = subcategory;
	}
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
}
