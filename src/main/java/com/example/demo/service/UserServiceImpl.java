package com.example.demo.service;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserLogin;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(User u) throws Exception{
		// TODO Auto-generated method stub
		try {
			u.setLasttime((int)Instant.now().getEpochSecond());
			userRepository.save(u);
		}catch(Exception ex) {
			throw ex;
		}
	
	}

	@Override
	public Page<User> findAll(String kw, Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findByKeyword(kw, pageable);
	}

	@Override
	public User findById(Integer uid) {
		// TODO Auto-generated method stub
		return userRepository.findById(uid).get();
	}

	@Override
	public void delete(User u) {
		// TODO Auto-generated method stub
		userRepository.delete(u);
		
	}

	@Override
	public void deleteById(Integer uid) {
		// TODO Auto-generated method stub
		userRepository.deleteById(uid);
	}

	@Override
	@Transactional
	public void deletes(List<User> users) {
		// TODO Auto-generated method stub
		for(User u: users) {
			userRepository.delete(u);
		}
	}

	/*
	 * 检测登陆用户是否合法用户
	 * @param user 登陆用户的账号密码
	 * @return 如果用户合法返回true,否则false
	 */
	@Override
	public User checkUser(UserLogin user) {
		User u = null;
		//去数据库中通过账号查找用户信息
		Optional<User> ou  = userRepository.findByAccount(user.getAccount());
		if(ou.isPresent()) {//isPresent()方法判断Optional中是否包含目标对象
			u = ou.get();
			//把库中的密码和登陆时密码比对
			if(u.getPassword().equals(user.getPassword())) {
				return u;
			}
		}
		return null;
	}
	
	
	

}
