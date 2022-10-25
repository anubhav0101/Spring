package com.example.application.request;

import java.util.List;

import com.example.application.entity.Subcategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AddProductRequest {

	private String name;
	private String details;
	private String category;
	private Integer price;
	private List<Subcategory> subCategory;
	
	public List<Subcategory> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(List<Subcategory> subCategory) {
		this.subCategory = subCategory;
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
