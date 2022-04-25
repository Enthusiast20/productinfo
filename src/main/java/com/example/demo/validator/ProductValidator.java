package com.example.demo.validator;

import com.example.demo.controller.exception.InvalidInputException;

public class ProductValidator {

	// the COMMA Constants
	private static final String COMMA = ",";
	
	public void handleMultipleValues(String type, Double minPrice, Double maxPrice, String city, String color, Integer gbLimitMin, Integer gbLimitMax)
	{
		if((type != null && type.contains(COMMA)) || (city != null && city.contains(COMMA)) || (color != null && color.contains(COMMA)))
				{
					throw new InvalidInputException("Multiple values are not allowed");
				}
	}
	
}
