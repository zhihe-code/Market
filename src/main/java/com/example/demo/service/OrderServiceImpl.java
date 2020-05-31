package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderRepository;
import com.example.demo.domain.Order;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRespository;
	

	@Override
	public void save(Order order) {
		orderRespository.save(order);
	}

	@Override
	public void delete(Order order) {
		orderRespository.delete(order);
	}

	@Override
	public void deleteById(Integer id) {
		orderRespository.deleteById(id);
	}

	@Override
	public void deletes(Iterable<Order> orders) {
		orderRespository.deleteAll(orders);
	}

	@Override
	public Order findById(Integer id) {
		Optional<Order> oc = orderRespository.findById(id);
		if(oc.isPresent()) {
			return oc.get();
		}
		return null;
	}

}
