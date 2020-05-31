package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>{
	public List<ProductType> findByParent(ProductType parent);
	
}
