package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ProductModel;

public interface ProductService {

	List<ProductModel> getProductDetails(String type, Double minPrice, Double maxPrice, String city, String color, Integer gbLimitMin, Integer gbLimitMax);

	
	//saving multipart file
	void save(MultipartFile file);
}
