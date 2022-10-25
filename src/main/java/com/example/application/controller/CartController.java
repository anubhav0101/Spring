package com.example.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.request.UpdateQuantity;
import com.example.application.response.CartItemResponse;
import com.example.application.response.CartResponse;
import com.example.application.response.ResultResponse;
import com.example.application.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartService;

	ResultResponse resultResp = new ResultResponse();

	@GetMapping("/{userid}/getCart")
	public ResponseEntity<?> GetCart(@PathVariable int userid) {
		try {
			List<CartResponse> gc = cartService.getCart(userid);
			return new ResponseEntity<List<CartResponse>>(gc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/{userid}/getCartItem/{cartitemId}")
	public ResponseEntity<?> GetCartItem(@PathVariable int userid,@PathVariable int cartitemId) {
		try {
			List<CartItemResponse> gc = cartService.getCartItem(userid,cartitemId);
			return new ResponseEntity<List<CartItemResponse>>(gc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{userid}/add/{productId}")
	public ResponseEntity<?> AddToCart(@PathVariable int userid,@PathVariable int productId) {
		try {
			List<CartItemResponse> gc = cartService.AddProductToCart(userid,productId);
			return new ResponseEntity<List<CartItemResponse>>(gc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{userid}/remove/{productId}")
	public ResponseEntity<?> RemoveFromCart(@PathVariable int userid,@PathVariable int productId) {
		try {
			 String gc = cartService.RemoveProductFromCart(userid,productId);
			 String result=gc+" removed from cart";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{userid}/changeQuantity/{productId}")
	public ResponseEntity<?> ChangeQuantity(@PathVariable int userid,@PathVariable int productId,@RequestBody UpdateQuantity req) {
		try {
			 List<CartItemResponse> gc = cartService.UpdateQuantityFromCart(userid,productId,req.getQuantity());
			return new ResponseEntity<List<CartItemResponse>>(gc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/changeQuantity/{cartItemId}")
	public ResponseEntity<?> ChangeQuantity(@PathVariable int cartItemId,@RequestBody UpdateQuantity req) {
		try {
			 CartItemResponse gc = cartService.UpdateQuantityFromCart(cartItemId,req.getQuantity());
			return new ResponseEntity<CartItemResponse>(gc, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.BAD_REQUEST);
		}
	}
}
