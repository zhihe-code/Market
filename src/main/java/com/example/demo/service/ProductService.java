package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.Product;
import com.example.demo.domain.Product.PVerify;
import com.example.demo.domain.Search;

public interface ProductService {
	public void save(Product product);
	public void delete(Product product);
	public void deleteById(Integer id);
	public void deletes(Iterable<Product> products);
	public Product findById(Integer id);
	public Page<Product> findBySerach(Search search,Pageable pageable);
	public Page<Product> findByVerify(PVerify verify,Pageable pageable);
}
