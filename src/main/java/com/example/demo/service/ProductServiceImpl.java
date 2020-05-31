package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductRepository;
import com.example.demo.domain.Product;
import com.example.demo.domain.Product.PVerify;
import com.example.demo.domain.Search;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepository ;

	@Override
	public void save(Product product) {
		if(product.getPid()==null) {			//发布日期
			product.setPubdate(LocalDateTime.now());
		}
		
		productRepository.save(product);
	}

	@Override
	public void delete(Product product) {
		productRepository.delete(product);
		
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deletes(Iterable<Product> products) {
		productRepository.deleteAll(products);
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> oc = productRepository.findById(id);
		if(oc.isPresent()) {
			return oc.get();
		}
		return null;
	}

	@Override
	public Page<Product> findBySerach(Search search, Pageable pageable) {
		if(pageable.getSort().isUnsorted()) {			//没排序
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
					Sort.by("pid").descending());			//页号、页面大小、逆序排序
		}
		return productRepository.findAll(new Specification<Product>(){
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, 		//root相当于from ， 查询语句 ，创建器
					CriteriaBuilder criteriaBuilder) {
				Predicate p = criteriaBuilder.conjunction();//与关系
				Predicate b = criteriaBuilder.disjunction();//或关系
				if(search.getKeyword()!=null&&!"".equals(search.getKeyword())) {
					//用关键字模糊查询，字段包括name和user
					String kw = "%"+search.getKeyword()+"%";
					Predicate a1 = criteriaBuilder.like(root.get("name").as(String.class), kw);
					Predicate a2 = criteriaBuilder.like(root.get("contents").as(String.class), kw);
					b.getExpressions().add(a1);
					b.getExpressions().add(a2);
				}
				if(b.getExpressions().size()>0) {//把或关系和与关系合并
					p.getExpressions().add(b);
				}
				return p;
			}},pageable);
	}

	@Override
	public Page<Product> findByVerify(PVerify verify, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByVerify(verify, pageable);
	}
	
	
}
