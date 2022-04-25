package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.CSVHelper;
import com.example.demo.controller.exception.ProductNotFoundException;
import com.example.demo.entity.Product;
import com.example.demo.model.CriteriaOperation;
import com.example.demo.model.ProductModel;
import com.example.demo.model.ProductSearchCriteria;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.speicification.ProductSpecification;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository repository;
	
	
	@Autowired
	CSVHelper csvHelper;
	
	
	public List<ProductModel> getProductDetails(String type, Double minPrice, Double maxPrice, String city, String color, Integer gbLimitMin, Integer gbLimitMax)
	{
		LOG.debug("Getting product details for type:{1}, minPrice:{2}, maxPrice:{3}, city:{4}, color:{5}, gbLimitMin:{6}, gbLimitMax:{7}",
				type, minPrice, maxPrice, city, color, gbLimitMin, gbLimitMax);
		
		ProductSpecification productSpecification = new ProductSpecification();
		productSpecification.add(new ProductSearchCriteria("type", type, CriteriaOperation.EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("city", city, CriteriaOperation.EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("color", color, CriteriaOperation.EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("gbLimitmin", gbLimitMin, CriteriaOperation.GREATER_THAN_OR_EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("gbLimitmax", gbLimitMax, CriteriaOperation.LESS_THAN_OR_EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("priceMin",minPrice , CriteriaOperation.GREATER_THAN_OR_EQUAL_TO));
		productSpecification.add(new ProductSearchCriteria("priceMax", maxPrice, CriteriaOperation.GREATER_THAN_OR_EQUAL_TO));
		
		List<Product> p1 = repository.findAll(Specification
							.where(productSpecification.withType(type))
							.and(productSpecification.withCity(city))
							.and(productSpecification.withColor(color))
							.and(productSpecification.withGbLimitGreaterThanEqualTo(gbLimitMin))
							.and(productSpecification.withGbLimitLessThanEqualTo(gbLimitMax))
							.and(productSpecification.withPriceGreaterThanEqualTo(minPrice))
							.and(productSpecification.withPriceLessThanEqualTo(maxPrice)));
		
		List<ProductModel> pmList = new ArrayList<>();
		if (p1 != null && p1.size()> 0)
		{
			LOG.debug("Final product list size:{}" + p1.size());
			
			for(Product p:p1)
			{
				ProductModel pm = new ProductModel();
				pm.setStore_address(p.getCity());
				pm.setType(p.getType());
				pm.setPrice(p.getPrice());
				if(p.getColor() != null)
				{
					pm.setProperties("color:" + p.getColor());
				}
				else if(p.getGbLimit() != null)
				{
					pm.setProperties("GbLimit:" + p.getGbLimit());
				}
				pmList.add(pm);
			}
		}
		else {
				LOG.debug("No product for given criteria");
				throw new ProductNotFoundException("No Products found with given criteria");
			 }
		return pmList;
	}
	
	//saving multipart file
	public void save(MultipartFile file)
	{
		try 
		{
			csvHelper.csvToDB(file.getInputStream());
		}
		catch(IOException e)
		{
			throw new RuntimeException("fail to store csv data:" + e.getMessage());
		}
	}
	
	
	public void setRepository(ProductRepository repository)
	{
		this.repository = repository;
	}

}
