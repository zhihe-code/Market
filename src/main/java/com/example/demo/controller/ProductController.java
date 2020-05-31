package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Product;
import com.example.demo.domain.ProductType;
import com.example.demo.domain.Search;
import com.example.demo.domain.User;
import com.example.demo.domain.Product.PVerify;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductTypeService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@RequestMapping("/listproducts")
	public String list(Search search,Model model,Pageable pageable) {
		model.addAttribute("search",search);
		Page<Product> p = productService.findBySerach(search, pageable);
		model.addAttribute("pages",p);
		return "/admin/listproducts";
	}
	
	@GetMapping({"/editproduct","/editproduct/{pid}"})
	public String edit(@PathVariable(name="pid",required=false)Integer pid,Model model) {
		Product p=null;
		if(pid==null) {
			p = new Product();
		}else {
			p = productService.findById(pid);
		}
		model.addAttribute("types",productTypeService.findByParent(null));
		model.addAttribute("product",p);
		return "editproduct";
	}
	
	@PostMapping("/saveproduct")
	public String save(@Valid Product product,BindingResult br,RedirectAttributes attr,HttpSession session) {
		ProductType pt = new ProductType();
		pt.setTid(1);
		product.setType(pt);
		User user = null;
		user = (User) session.getAttribute("user");
		product.setUser(user);
		productService.save(product);
		attr.addFlashAttribute("ok","商品添加成功");
		return "redirect:/index";
		
	}
	@GetMapping("/validproduct/{pid}")
	public String validuser(@PathVariable("pid")Integer pid) {
		Product product = productService.findById(pid);
		
		PVerify valid =product.getValidstate();
		
		if(valid==PVerify.待审) product.setValidstate(PVerify.通过);
		if(valid==PVerify.通过) product.setValidstate(PVerify.待审);
		productService.save(product);
		return "redirect:/listproducts";
	}
}
