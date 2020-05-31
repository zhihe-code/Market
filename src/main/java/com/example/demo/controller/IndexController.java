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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.demo.domain.Product;
import com.example.demo.domain.Product.PVerify;
import com.example.demo.domain.Search;
import com.example.demo.domain.User;
import com.example.demo.domain.User.Role;
import com.example.demo.domain.User.Verify;
import com.example.demo.domain.UserLogin;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;



@Controller
public class IndexController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;

	@GetMapping({"/index"})
	public String index(Search search,Model model,Pageable pageable) {
		User u = new User();
		model.addAttribute("user",u);
		model.addAttribute("sexes",User.Sex.toList());
		model.addAttribute("userLogin", new UserLogin());
		model.addAttribute("search",search);
		
		PVerify verify = PVerify.通过;
		Page<Product> pp = productService.findByVerify(verify, pageable);
		model.addAttribute("pages",pp);
		return "index";
	}

	
	
	@PostMapping(value="/index")
	public String login(@Valid UserLogin user, BindingResult result,
			HttpSession session,Search search,Model model,Pageable pageable,RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("fail","错误用户不存在");
			return "redirect:/index";
		}
		model.addAttribute("search",search);
		PVerify pverify = PVerify.通过;
		Page<Product> pp = productService.findByVerify(pverify, pageable);
		model.addAttribute("pages",pp);
		
		
		
		//检查用户身份的方法
		User u =userService.checkUser(user);
		if(u==null) {
			attr.addFlashAttribute("fail","用户不存在");
		}
		else {
			Verify verify = u.getValidstate();
			Role role = u.getRole();
			if(verify==Verify.通过) {
				if(role==Role.普通用户) {
					session.setAttribute("user",u);
					return "index";
				}
				else if(role==Role.审核员||role==Role.管理员) {
					session.setAttribute("user",u);
					return "admin/index";//管理主界面
				}
			}
			else {
				attr.addFlashAttribute("fail","用户审核中,请等待通过后再试!");
				session.removeAttribute("user");
				return "redirect:/index";
			}
		}
		return "redirect:/index";
	}
	
	
	@PostMapping("/register")
	public String save(@Valid User user, BindingResult result,RedirectAttributes attr) {
		try {
			if(result.hasErrors()) {
				return "redirect:/index";
			}
			if(user.getUid()!=null && user.getUid()>0) {
				User u = userService.findById(user.getUid());
				user.setPassword(u.getPassword());
			}
			userService.save(user);
			attr.addFlashAttribute("ok","注册成功！");
			return "redirect:/index";
			
		}catch (Exception ex) {
			return "redirect:/index";
		}

	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/index";
	}
	

	

	

	
}
