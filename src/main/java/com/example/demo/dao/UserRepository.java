package com.example.demo.dao;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	
	@Query("select u from User u where account like ?1 or name like ?1 or mobile like ?1 or email like ?1")
	public Page<User> findByKeyword(String kw,Pageable pageable);
	
	//修改密码
	@Query("update User u set u.password=?1 where u.uid=?2")
	public void modifyPassword(String pwd,Integer uid);
	
	//通过账号查询用户信息，获取唯一的用户
	public Optional<User> findByAccount(String account);

}
