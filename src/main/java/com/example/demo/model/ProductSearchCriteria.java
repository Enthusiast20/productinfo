package com.example.demo.model;

public class ProductSearchCriteria {

	
	private String columnName;
	private Object columnValue;
	private CriteriaOperation operation;
	
	
	public ProductSearchCriteria(String columnName, Object columnValue, CriteriaOperation operation)
	{
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.operation = operation;
		
				
	}


	public String getColumnName() {
		return columnName;
	}


	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public Object getColumnValue() {
		return columnValue;
	}


	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}


	public CriteriaOperation getOperation() {
		return operation;
	}


	public void setOperation(CriteriaOperation operation) {
		this.operation = operation;
	}
	
	
	
	
}
