package com.example.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.entity.OrderEntity;
import com.example.application.response.ResultResponse;
import com.example.application.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	
	@Autowired
	OrderService orderService;
	ResultResponse resultResp = new ResultResponse();

	@GetMapping("/{userId}/createOrder")
	public ResponseEntity<?> createOrder(@PathVariable int userId) {
		try {
			List<OrderEntity> resp = orderService.create(userId);
			return new ResponseEntity<List<OrderEntity>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{userId}/getOrders")
	public ResponseEntity<?> getOrders(@PathVariable int userId) {
		try {
			List<OrderEntity> resp = orderService.getOrder(userId);
			return new ResponseEntity<List<OrderEntity>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponse>(resultResp, HttpStatus.NOT_FOUND);
		}
	}

}
