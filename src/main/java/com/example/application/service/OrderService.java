package com.example.application.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.Repo.CartRepo;
import com.example.application.Repo.OrderRepo;
import com.example.application.Repo.ProductRepo;
import com.example.application.Repo.UserRepo;
import com.example.application.entity.Cart;
import com.example.application.entity.OrderEntity;
import com.example.application.entity.Users;
import com.example.application.response.CartItemResponse;
import com.example.application.response.CartResponse;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	CartRepo cartRepo;

	public List<OrderEntity> create(int userId) {
		
		List<Cart> cart_item_list = cartRepo.findByUsers(userRepo.findEntityById(userId));
		List<OrderEntity> result_order = new ArrayList<>();
		for(int i=0;i<cart_item_list.size();i++) {
			OrderEntity order=new OrderEntity();
			if(orderRepo.count()==0)
			order.setOrder_id(1);
			else
			order.setOrder_id(orderRepo.max()+1);
			order.setUsers(userRepo.findEntityById(userId));
			order.setOrder_status("Successful");
			order.setCart_item_id(cart_item_list.get(i));
			result_order.add(order);			
		}
		orderRepo.saveAll(result_order);
		return result_order;
		
	}
	public List<OrderEntity> getOrder(int userId) {
		List<OrderEntity> userOrder = orderRepo.findByUsers(userRepo.findEntityById(userId));
		return userOrder;
	}

}
