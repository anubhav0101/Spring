package com.example.application.Repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Cart;
import com.example.application.entity.Users;

@Repository
@Component
public interface CartRepo extends JpaRepository<Cart, Integer> {

	List<Cart> findById(int id);

	default Cart findEntityById(int id) {
		return findById(id).get(0);
	}	
	List<Cart> findByUsers(Users users);
	@Query(value = "SELECT max(cart_id) FROM Cart")
	public int max();
}
