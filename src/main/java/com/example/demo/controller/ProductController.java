package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.CSVHelper;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ProductService;
import com.example.demo.validator.ProductValidator;

@RestController
public class ProductController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

@Autowired
ProductService service;

@Autowired
CSVHelper csvHelper;


//upload CSV file to database
@PostMapping(path = "/upload", consumes = "multipart/form-data")
public String uploadFile(@RequestPart("file") MultipartFile file)
{
	LOG.debug("Inside the uploaded file");
	String message = "";
	if(csvHelper.hasCSVFormat(file))
	{
		try
		{
			service.save(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return "File is uploaded successfully";
		}
		catch(Exception e)
		{
			message = "Could not upload the file: "+ file.getOriginalFilename() + "!";
			return "Unable to upload the file";
		}
	}
	
	message = "Upload the CSV file!";
	return "message";
	
}

@GetMapping(path = "/product")
public List<ProductModel> getProductDetails(
		@RequestParam(name = "type", required = false) String type,
		@RequestParam(name = "min_price", required = false) Double min_price,
		@RequestParam(name = "max_price", required = false) Double max_price,
		@RequestParam(name = "city", required = false) String city,
		@RequestParam(name = "color", required = false) String color,
		@RequestParam(name = "gb_limit_min", required = false) Integer gbLimitMin,
		@RequestParam(name = "gb_limit_max", required = false) Integer gbLimitMax)

			{
				LOG.debug("Inside getProductDetails method");
				ProductValidator validator = new ProductValidator();
				validator.handleMultipleValues(type, min_price, max_price, city, color, gbLimitMin, gbLimitMax);
				List<ProductModel> productList = service.getProductDetails(type, min_price, min_price, city, color, gbLimitMin, gbLimitMax);
				return productList;
				
			}
		


@GetMapping(path = "/isAvailable")
public String isAvailable()
{
	LOG.info("verifying service availability");
	return "Available";
}


}