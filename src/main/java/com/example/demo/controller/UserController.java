package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/listusers")
	public String list(String kw,Model model,Pageable pageable) {
		model.addAttribute("kw",kw);
		if(kw!=null) kw="%"+kw+"%";
		if(kw==null) kw="%%";
		Page<User> ppu = userService.findAll(kw, pageable);//默认分页从0页面（第一页），取每页20条数据
		model.addAttribute("pages",ppu);
		return "/admin/listusers";//返回界面
	}
	
	@GetMapping({"/edituser","/edituser/{uid}","/admin/edituser"})
	public String edit(@PathVariable(name="uid",required=false)Integer uid,Model model) {
		User u = new User();
		if(uid!=null&&uid>0) {
			u=userService.findById(uid);
		}
		model.addAttribute("sexes",User.Sex.toList());
		model.addAttribute("limits",User.Verify.toList());
		model.addAttribute("roles",User.Role.toList());
		model.addAttribute("user",u);
		return "/admin/edituser";
	}
	
	@PostMapping("/saveuser")
	public String save(@Valid User user, BindingResult result,RedirectAttributes attr) {
		try {
			if(result.hasErrors()) {
				return "redirect:/listusers";
			}
			if(user.getUid()!=null && user.getUid()>0) {
				User u = userService.findById(user.getUid());
				user.setPassword(u.getPassword());
			}
			userService.save(user);
			attr.addFlashAttribute("ok","保存成功！");
			return "redirect:/listusers";
			
		}catch (Exception ex) {
			return "redirect:/listusers";
		}

	}
	
	@GetMapping("/deleteuser/{uid}")
	public String delete(@PathVariable("uid")Integer uid) {
		userService.deleteById(uid);
		return "redirect:/admin/listusers";
	}
	
	@PostMapping("/deleteusers")
	public String deletes(String uids) {
		System.out.println("============="+uids);
		List<User> users = new ArrayList<>();
		JSONObject json = JSONObject.parseObject(uids);
		JSONArray arr = json.getJSONArray("uids"); //前端传递时使用uids作为json数据的键
		int ilen = arr.size();
		for(int i=0;i<ilen;i++) {//每次查询再去删除，效率低下
			users.add(userService.findById(arr.getInteger(i)));
		}
		userService.deletes(users);
		return "redirect:/admin/listusers";
	}
	
//	@GetMapping("/validuser/{uid}")
//	public String validuser(@PathVariable("uid")Integer uid) {
//		User user =userService.findById(uid);
////		if(user.getValidstate()==0) user.setValidstate(1);
////		if(user.getValidstate()==1) user.setValidstate(0);
//		user.setValidstate(1-user.getValidstate());
//		return "redirect:/listusers";
//	}
}
