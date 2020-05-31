package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.domain.User;

public class CheckUserInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		HttpSession session = request.getSession(); //获得会话对象
		User user = (User) session.getAttribute("user"); //得到会话范围内的user对象        
        if (uri.startsWith("/admin") && (user==null||user.getUid()==null||user.getUid()<=0)) {
            response.sendRedirect("/index");
            return false;
        } else {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
		
	}
	

}
