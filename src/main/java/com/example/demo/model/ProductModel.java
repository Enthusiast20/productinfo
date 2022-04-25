package com.example.demo.model;

public class ProductModel {

	String type;
	
	// the price in SEK
	Double price;
	
	// the city in which a store is located
	String store_address;
	
	//properties of product type
	String properties;
	
	
	public ProductModel()
	{
		super();
	}
	
	public ProductModel(String type, Double price, String store_address, String properties)
	{
		super();
		this.type = type;
		this.price = price;
		this.store_address = store_address;
		this.properties = properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	
	
}
