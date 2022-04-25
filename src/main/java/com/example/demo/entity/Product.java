package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	Long id;
	String type;
	
	//The price in SEK
	Double price; 
	
	//City in which a store is located
	String city;
	
	//color property of phone
	String color;
	
	Integer gbLimit;
	
	
	public Product()
	{
		
	}
	
	
	
	public Product(String type, Double price, String city, String color, Integer gbLimit)
	{
		super();
		this.type = type;
		this.price = price;
		this.city = city;
		this.color = color;
		this.gbLimit = gbLimit;
	}
	
	
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 40)
	public Long getId()
	{
		return id;
	}
	
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	@Column(name="type", length = 40)
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	@Column(name="price", length = 40)
	public Double getPrice()
	{
		return price;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	@Column(name="city", length = 40)
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}


	@Column(name="color", length = 500)
	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}


	@Column(name="gb_limit", length = 500)
	public Integer getGbLimit() {
		return gbLimit;
	}



	public void setGbLimit(Integer gbLimit) {
		this.gbLimit = gbLimit;
	}
	
	@Override
	public String toString()
	{
		return "Product [id=" + id + ", type= "+ type + ", price=" + price + ", city=" + city + ", attributeName=" + color + ", gbLimit=" + gbLimit + "]";
	}

}
