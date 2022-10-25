package com.example.application.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.OrderEntity;
import com.example.application.entity.Users;

@Repository
@Component
public interface OrderRepo extends JpaRepository<OrderEntity, Integer>  {
	List<OrderEntity> findById(int id);

	default OrderEntity findEntityById(int id) {
		return findById(id).get(0);
	}	
	List<OrderEntity> findByUsers(Users users);
	@Query(value = "SELECT max(order_id) FROM OrderEntity")
	public int max();
	
}
