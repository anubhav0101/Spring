package com.example.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Repo.CartRepo;
import com.example.application.Repo.ProductRepo;
import com.example.application.Repo.UserRepo;
import com.example.application.entity.Cart;
import com.example.application.entity.Users;
import com.example.application.response.CartItemResponse;
import com.example.application.response.CartResponse;

@Service
public class CartService {
	@Autowired
	CartRepo cartRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	UserRepo userRepo;

	public List<CartResponse> getCart(int id) throws Exception {
		Users user = userRepo.findEntityById(id);
		List<Cart> userCart = cartRepo.findByUsers(user);
		List<CartResponse> cart = new ArrayList<>();
		for (int i = 0; i < userCart.size(); i++) {
			CartResponse cartResponse = new CartResponse();
			cartResponse.setCartId(userCart.get(i).getCart_id());
			cartResponse.setProducts(userCart.get(i).getProducts());
			cart.add(cartResponse);
		}
		return cart;
	}

	public List<CartItemResponse> getCartItem(int userid, int cartitemId) {
//		Users user =userRepo.findEntityById(userid);
//		List<Cart> userCart = cartRepo.findByUsers(user);
		// add validation if user cart entity is equal to findbyid entity or not
		List<Cart> cartItem = cartRepo.findById(cartitemId);
		List<CartItemResponse> cart = new ArrayList<>();
		for (int i = 0; i < cartItem.size(); i++) {
			CartItemResponse cartItemResponse = new CartItemResponse();
			cartItemResponse.setCartItemId(cartItem.get(i).getId());
			cartItemResponse.setProducts(cartItem.get(i).getProducts());
			cartItemResponse.setQuantity(cartItem.get(i).getQuantity());
			cart.add(cartItemResponse);
		}
		return cart;
	}

	public List<CartItemResponse> AddProductToCart(int userid, int productId) {
		
		Users user = userRepo.findEntityById(userid);
		System.out.println(user);
		if(cartRepo.findByUsers(user).isEmpty())
		{
			Cart ct=new Cart();
			ct.setProducts(productRepo.findEntityById(productId));
			ct.setQuantity(1);
			ct.setUsers(user);
			if(cartRepo.count()==0)
				ct.setCart_id(1);
			else
			ct.setCart_id(cartRepo.max()+1);
			System.out.println(ct.getCart_id());
			cartRepo.save(ct);
			List<CartItemResponse> cart = new ArrayList<>();
			CartItemResponse cartItemResponse = new CartItemResponse();
			cartItemResponse.setCartItemId(ct.getId());
			cartItemResponse.setProducts(ct.getProducts());
			cartItemResponse.setQuantity(ct.getQuantity());
			cart.add(cartItemResponse);
			return cart;
		}
		else {
		List<Cart> userCart = cartRepo.findByUsers(user);
		for (int i = 0; i < userCart.size(); i++) {
			if (userCart.get(i).getProducts() == productRepo.findEntityById(productId)) {
				userCart.get(i).setQuantity(userCart.get(i).getQuantity() + 1);
				cartRepo.saveAll(userCart);
				List<CartItemResponse> cart = new ArrayList<>();
					CartItemResponse cartItemResponse = new CartItemResponse();
					cartItemResponse.setCartItemId(userCart.get(i).getId());
					cartItemResponse.setProducts(userCart.get(i).getProducts());
					cartItemResponse.setQuantity(userCart.get(i).getQuantity());
					cart.add(cartItemResponse);
				return cart;
			}
		}
		Cart resultcart = new Cart();
		resultcart.setUsers(user);
		resultcart.setProducts(productRepo.findEntityById(productId));
		resultcart.setQuantity(1);
		resultcart.setCart_id(cartRepo.findByUsers(user).get(0).getCart_id());
		cartRepo.save(resultcart);
		List<CartItemResponse> cart = new ArrayList<>();
		CartItemResponse cartItemResponse = new CartItemResponse();
		cartItemResponse.setCartItemId(resultcart.getId());
		cartItemResponse.setProducts(resultcart.getProducts());
		cartItemResponse.setQuantity(resultcart.getQuantity());
		cart.add(cartItemResponse);
		return cart;
		}
	}

	public String RemoveProductFromCart(int userid, int productId) {
		Users user = userRepo.findEntityById(userid);
		List<Cart> userCart = cartRepo.findByUsers(user);
		for (int i = 0; i < userCart.size(); i++) {
			if (userCart.get(i).getProducts() == productRepo.findEntityById(productId)) {
				cartRepo.delete(userCart.get(i));
				return productRepo.findEntityById(productId).getName();
			}
		}
		return "nothing";
	}

	public List<CartItemResponse> UpdateQuantityFromCart(int userid, int productId,Integer quantity) {
		Users user = userRepo.findEntityById(userid);
		List<Cart> userCart = cartRepo.findByUsers(user);
		for (int i = 0; i < userCart.size(); i++) {
			if (userCart.get(i).getProducts() == productRepo.findEntityById(productId)) {
				userCart.get(i).setQuantity(quantity);
				cartRepo.saveAll(userCart);
				List<CartItemResponse> cart = new ArrayList<>();
				CartItemResponse cartItemResponse = new CartItemResponse();
				cartItemResponse.setCartItemId(userCart.get(i).getId());
				cartItemResponse.setProducts(userCart.get(i).getProducts());
				cartItemResponse.setQuantity(userCart.get(i).getQuantity());
				cart.add(cartItemResponse);
			return cart;
			}
		}
		return null;
	}
	public CartItemResponse UpdateQuantityFromCart(int cartItemId,Integer quantity) {
		Cart usCart = cartRepo.findEntityById(cartItemId);
		usCart.setQuantity(quantity);
		cartRepo.save(usCart);
		CartItemResponse cartItemResponse = new CartItemResponse();
		cartItemResponse.setCartItemId(usCart.getId());
		cartItemResponse.setProducts(usCart.getProducts());
		cartItemResponse.setQuantity(usCart.getQuantity());
	return cartItemResponse;
	}
}