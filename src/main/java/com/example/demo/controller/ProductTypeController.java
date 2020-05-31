package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.ProductType;
import com.example.demo.service.ProductTypeService;

@Controller
public class ProductTypeController {
	@Autowired
	private ProductTypeService contentTypeService;
	@GetMapping({"/edittype","/admin/edittype/{tid}"})
	public String edit(@PathVariable(name="tid", required=false)Integer tid,Model model) {
		ProductType type=null; 
		if(tid==null) {//新增
			type=new ProductType();
		}else {
			type = contentTypeService.findById(tid);
		}
		model.addAttribute("parents",contentTypeService.findByParent(null));
		model.addAttribute("productType",type);
		return "admin/edittype";
	}
	
	@PostMapping("/savetype")
	public String save(@Valid ProductType type,BindingResult result,Model model) {
		model.addAttribute("productType",type);
		if(result.hasErrors()) {
			
		}
		contentTypeService.save(type);
		model.addAttribute("ok","保存成功！");
		return "redirect:/admin/edittype/"+type.getTid();
		
	}
	
	@GetMapping("/admin/deletetype/{tid}")
	public String delete(@PathVariable("tid")Integer tid,Model model) {
		contentTypeService.deleteById(tid);
		model.addAttribute("ok","删除成功！");
		return "redirect:/admin/edittype";
	}

}
