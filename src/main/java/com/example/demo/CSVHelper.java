package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.h2.tools.Csv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.exception.InvalidCSVFileException;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Component
public class CSVHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(CSVHelper.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@PostConstruct
	public void loadCsvData()
	{
		LOG.debug("loading the data from CSV");
		ResultSet rs;
		try
		{
			rs = new Csv().read("classpath:data.csv", null, "UTF-8");
			
			while(rs.next())
			{
				Product pc = new Product();
				pc.setType(rs.getString("Product type"));
				pc.setCity(rs.getString("Store address"));
				pc.setPrice(rs.getDouble("Price"));
				String productProperties = rs.getString("Product properties");
				if("color".equals(productProperties.split(":")[0]))
					{
						pc.setColor(productProperties.split(":")[1]);
					}
				if("gb_Limit".equals(productProperties.split(":")[0]))
				{
					pc.setGbLimit(Integer.valueOf(productProperties.split(":")[1]));
				}
				productRepository.save(pc);
			}
		}
		
		catch(SQLException e)
		{
			LOG.error("unable to load the data from csv", e);
			//throw new InvalidCSVFileException("Unable to read CSV and write to database");
		}
		
		
	}
	
	
	public boolean hasCSVFormat(MultipartFile file)
	{
		if(!file.getOriginalFilename().contains(".csv"))
		{
			return false;
		}
		return true;
	}
	
	public void csvToDB(final InputStream is)
	{
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));)
		{
			ResultSet rs = new Csv().read(fileReader, null);
			while(rs.next())
			{
				Product pc = new Product();
				pc.setType(rs.getString("Product type"));
				pc.setCity(rs.getString("Store address"));
				pc.setPrice(rs.getDouble("Price"));
				String productProperties = rs.getString("Product properties");
				if("color".equals(productProperties.split(":")[0]))
					{
						pc.setColor(productProperties.split(":")[1]);
					}
				if("gb_Limit".equals(productProperties.split(":")[0]))
				{
					pc.setGbLimit(Integer.valueOf(productProperties.split(":")[1]));
				}
				productRepository.save(pc);
			}
		} catch(IOException e) {
				throw new InvalidCSVFileException("Unable to read CSV and write to database");
		} catch(SQLException e) {
			throw new RuntimeException("Unable to read the data: " + e.getMessage());
		}
	}
	
	public ProductRepository getProductRepository()
	{
		return productRepository;
	}
	
	public void setProductRepository(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}
}
