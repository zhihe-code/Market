package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("/ue")
public class UEditController{
	@RequestMapping("editor")
	@ResponseBody
	public String editor(HttpServletRequest request,HttpServletResponse response) {
//	    request.setCharacterEncoding( "utf-8" );
		response.setHeader("Content-Type" , "text/html");
		String rootPath =request.getServletContext().getRealPath( "/" );
		return new ActionEnter( request, rootPath ).exec();
	}

}
