package br.com.crisaltmann.stock;

import java.math.BigDecimal;

public class Stock {

	private String id;
	
	private BigDecimal value;

	public Stock(String id, BigDecimal value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
}
