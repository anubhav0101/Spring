package com.example.application.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="product")
public class Products {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	private String category;
	private String details;
	private Integer price;
	private String name;
	
	@ManyToMany
	@JoinTable(
	 name = "pd_subcat", 
	 joinColumns = @JoinColumn(name = "pid"), 
	 inverseJoinColumns = @JoinColumn(name = "subcatid"))
	 private Set<Subcategory> subcategory = new HashSet<>();

	public Set<Subcategory> getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Set<Subcategory> subcategory) {
		this.subcategory = subcategory;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
}
