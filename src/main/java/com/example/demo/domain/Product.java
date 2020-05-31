package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;
	@NotNull
	private String name;	//商品名
	
	private Integer costprice; //原价
	
	private Integer price;  //二手价
	
	
	private String timg;	//商品图片
	
	@Lob
	private String contents;  //商品描述
	
	private LocalDateTime pubdate;//发布时间
	
	@ManyToOne
	@JoinColumn(name="tid")
	private ProductType type;  //商品类型
	
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;			//商品主人
	
	
	public enum PVerify{ 		//商品审核
		待审,驳回,通过;
		public static List<String> toList(){
			PVerify[] verify = PVerify.values();
			List<String> datas = new ArrayList<>();//定义一个列表容纳所有枚举的数据
			for(PVerify v:verify) {
				datas.add(v.name());
			}
			return datas;
		}
	};
	//用户是否有效
	private PVerify validstate=PVerify.待审;
	
	public PVerify getValidstate() {
		return validstate;
	}
	public void setValidstate(PVerify validstate) {
		this.validstate = validstate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCostprice() {
		return costprice;
	}
	public void setCostprice(Integer costprice) {
		this.costprice = costprice;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public LocalDateTime getPubdate() {
		return pubdate;
	}
	public void setPubdate(LocalDateTime pubdate) {
		this.pubdate = pubdate;
	}
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getTimg() {
		return timg;
	}
	public void setTimg(String timg) {
		this.timg = timg;
	}

	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
