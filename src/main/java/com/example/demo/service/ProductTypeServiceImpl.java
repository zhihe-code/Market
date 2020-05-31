package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductTypeRepository;
import com.example.demo.domain.ProductType;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
	@Autowired
	private ProductTypeRepository contentTypeRepository;

	@Override
	public void save(ProductType type) {
		// TODO Auto-generated method stub
		if(type.getParent()!=null&&type.getParent().getTid()==null) type.setParent(null);
		contentTypeRepository.save(type);
		
	}

	@Override
	public void delete(ProductType type) {
		// TODO Auto-generated method stub
		contentTypeRepository.delete(type);
	}

	@Override
	@Transactional
	public void deletes(Iterable<ProductType> types) {
		// TODO Auto-generated method stub
		contentTypeRepository.deleteAll(types);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		contentTypeRepository.deleteById(id);
		
	}

	@Override
	public List<ProductType> findByParent(ProductType parent) {
		// TODO Auto-generated method stub
		return contentTypeRepository.findByParent(parent);
	}

	@Override
	public List<ProductType> findAll() {
		// TODO Auto-generated method stub
		return contentTypeRepository.findAll();
	}

	@Override
	public ProductType findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<ProductType> oct = contentTypeRepository.findById(id);
		if(oct.isPresent()) return oct.get();
		return null;
	}
	

}
