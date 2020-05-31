package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.ProductType;

public interface ProductTypeService {
	public void save(ProductType type);
	public void delete(ProductType type);
	public void deletes(Iterable<ProductType> types);
	public void deleteById(Integer id);
	public List<ProductType> findByParent(ProductType parent);
	public List<ProductType> findAll();
	public ProductType findById(Integer id);

}
