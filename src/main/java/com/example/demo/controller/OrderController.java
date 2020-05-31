package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

@Controller
public class OrderController {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;

	@GetMapping({"/order","/order/{pid}"})
	public String order(@PathVariable(name="pid",required=false)Integer pid,Model model,HttpSession session) {
		Product p=null;
		
		if(pid==null) {
			p = new Product();
		}else {
			p = productService.findById(pid);
		}
		model.addAttribute("product", p);
		session.setAttribute("product", p);
		model.addAttribute("order", new Order());
		return "order";
	}
	
	
	@PostMapping("/saveorder")
	public String shop(@Valid Order order,BindingResult result
					,RedirectAttributes attr,HttpSession session) {
			if(result.hasErrors()) {
				return "redirect:/index";
			}
			Product product = (Product) session.getAttribute("product");
			order.setProduct(product);
			User seller = product.getUser();
			order.setSeller(seller);
			
			User buyer  = (User) session.getAttribute("user");
			order.setBuyer(buyer);
			
			orderService.save(order);
			attr.addFlashAttribute("ok","交易成功！");
			
			return "redirect:/index";
	}

}
