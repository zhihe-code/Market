package com.example.demo.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;




@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer uid;
	@Column(length=30,unique=true)
	@NotNull
	private String account;
	@Column(length=30)
	@NotNull
	private String password;
	@Column(length=30)
	private String name;
	public enum Sex{
		男,女;
		public static List<String> toList(){
			Sex[] sex = Sex.values();
			List<String> datas = new ArrayList<>();//定义一个列表容纳所有枚举的数据
			for(Sex s:sex) {
				datas.add(s.name());
			}
			return datas;
		}
	};
	private Sex grander;
	private LocalDate birthday;
	@Column(length=11)
	private String mobile;
	@Column(length=100)
	private String email;
	//最后登录时间
	private Integer lasttime; 
	
	public enum Verify{
		待审,驳回,通过;
		public static List<String> toList(){
			Verify[] verify = Verify.values();
			List<String> datas = new ArrayList<>();//定义一个列表容纳所有枚举的数据
			for(Verify v:verify) {
				datas.add(v.name());
			}
			return datas;
		}
	};
	//用户是否有效
	private Verify validstate=Verify.待审;
	
	public enum Role{
		普通用户,审核员,管理员;
		public static List<String> toList(){
			Role[] role = Role.values();
			List<String> datas = new ArrayList<>();//定义一个列表容纳所有枚举的数据
			for(Role r:role) {
				datas.add(r.name());
			}
			return datas;
		}
	};
	//用户权限
	private Role role=Role.普通用户;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getGrander() {
		return grander;
	}
	public void setGrander(Sex grander) {
		this.grander = grander;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLasttime() {
		return lasttime;
	}
	public void setLasttime(Integer lasttime) {
		this.lasttime = lasttime;
	}
	public Verify getValidstate() {
		return validstate;
	}
	public void setValidstate(Verify validstate) {
		this.validstate = validstate;
	}
	

}
