package com.example.demo.service;



import com.example.demo.domain.Order;

public interface OrderService {
	public void save(Order order);
	public void delete(Order order);
	public void deleteById(Integer id);
	public void deletes(Iterable<Order> orders);
	public Order findById(Integer id);

}
