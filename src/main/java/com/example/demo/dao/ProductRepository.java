package com.example.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Product;
import com.example.demo.domain.Product.PVerify;

public interface ProductRepository extends JpaRepository<Product,Integer>,
			JpaSpecificationExecutor<Product>{
	@Query("select p from Product p where validstate = ?1")
	public Page<Product> findByVerify(PVerify valid,Pageable pageable);
	
}
