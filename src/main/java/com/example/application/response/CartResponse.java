package com.example.application.response;

import com.example.application.entity.Products;

public class CartResponse {

	
	private Integer cartId;
	private Products products;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	
}
