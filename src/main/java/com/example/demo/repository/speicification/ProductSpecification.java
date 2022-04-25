package com.example.demo.repository.speicification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entity.Product;
import com.example.demo.model.CriteriaOperation;
import com.example.demo.model.ProductSearchCriteria;

public class ProductSpecification {

	
	private static final Logger LOG = LoggerFactory.getLogger(ProductSpecification.class);
	
	private List<ProductSearchCriteria> searchCriteria;
	
	
	public ProductSpecification()
	{
		super();
		this.searchCriteria = new ArrayList<>();
		
	}
	
	public void add(ProductSearchCriteria criteria)
	{
		this.searchCriteria.add(criteria);
	}
	
	
	//criteria query
	
	public String getCriteriaQuery()
	{
		LOG.debug("Building the criteria query");
		StringBuilder query = new StringBuilder("Select prod from Product where");
		boolean isFirstPass = true;
		for(ProductSearchCriteria criteria : searchCriteria)
		{
			if(criteria.getColumnValue()!= null)
			{
				isFirstPass = false;
				System.out.println("criteria" + criteria.getColumnValue().toString());
				if(criteria.getOperation().equals(CriteriaOperation.EQUAL_TO))
				{
					query.append(criteria.getColumnName()).append("=").append((String) criteria.getColumnValue()).append("");
				}
				else if(criteria.getOperation().equals(CriteriaOperation.GREATER_THAN_OR_EQUAL_TO))
				{
					query.append(criteria.getColumnName()).append(">=").append((Double) criteria.getColumnValue()).append("");
				}
				else if(criteria.getOperation().equals(CriteriaOperation.LESS_THAN_OR_EQUAL_TO))
				{
					query.append(criteria.getColumnName()).append("<=").append((Double) criteria.getColumnValue()).append("");
				}
				if(!isFirstPass)
						query.append(" and ");
					
				}
			}
		
		return !isFirstPass
						? query.substring(0, query.lastIndexOf("and"))
						: query.substring(0, query.lastIndexOf("where"));
		}
	
	public Specification<Product> withCity(String city)
	{
		if(city == null)
		{
			return null;
		}
			else
			{
				//Specification using Java 8 lambdas
				return (root, query, cb) -> cb.equal(root.get("city"), city);
				
			}
		}
		
	
	public Specification<Product> withType(String type)
	{
		if(type == null)
		{
			return null;
		}
			else
			{
				//Specification using Java 8 lambdas
				return (root, query, cb) -> cb.equal(root.get("type"), type);
				
			}
		}
		
	
	
	public Specification<Product> withPriceGreaterThanEqualTo(Double price)
	{
		if(price == null)
		{
			return null;
		}
			else
			{
				//Specification using Java 8 lambdas
				return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), price);
				
			}
		}
		
	
	
	public Specification<Product> withPriceLessThanEqualTo(Double price)
	{
		if(price == null)
		{
			return null;
		}
			else
			{
				//Specification using Java 8 lambdas
				return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), price);
				
			}
		}
		
	
	
	//findBysearchcriteria
	public Specification<Product> findBySearchCriteria()
	{
		return (root, query, criteriaBuilder)  -> {
			List<Predicate> predicates = new ArrayList<>();
			for (ProductSearchCriteria criteria : searchCriteria)
			{
				if(criteria.getColumnValue()!= null)
				{
					if(criteria.getOperation() == CriteriaOperation.EQUAL_TO)
					{
						predicates.add(criteriaBuilder.equal(root.get(criteria.getColumnName()), (String) criteria.getColumnValue()));
					}
					else if(criteria.getOperation() == CriteriaOperation.GREATER_THAN_OR_EQUAL_TO)
					{
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getColumnName()), (Double) criteria.getColumnValue()));
					}
					else if(criteria.getOperation() == CriteriaOperation.LESS_THAN_OR_EQUAL_TO)
					{
						predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getColumnName()), (Double) criteria.getColumnValue()));
					}
				}
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	
	public Specification<Product> withColor(String color)
	{
		if(color == null)
		{
			return null;
		}
		else
		{
			//Specification using Java 8 Lambdas
			return (root, query, cb) -> cb.equal(root.get("color"), color);
		}
	}
	
	public Specification<Product> withGbLimitGreaterThanEqualTo(Integer gbLimit)
	{
		if(gbLimit == null)
		{
			return null;
		}
		else
		{
			//Specification using Java 8 Lambdas
			return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("gbLimit"), gbLimit);
		}
	}
	
	
	public Specification<Product> withGbLimitLessThanEqualTo(Integer gbLimit)
	{
		if(gbLimit == null)
		{
			return null;
		}
		else
		{
			//Specification using Java 8 Lambdas
			return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("gbLimit"), gbLimit);
		}
	}
	
}
