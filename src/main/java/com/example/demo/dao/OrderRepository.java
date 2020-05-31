package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.domain.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>
					,JpaSpecificationExecutor<Order>{

}
