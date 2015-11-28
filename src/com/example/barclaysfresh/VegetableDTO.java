package com.example.barclaysfresh;

public class VegetableDTO {

	private String name;
	private int priceKg;
	private boolean isAddedToCart;
	
	public boolean isAddedToCart() {
		return isAddedToCart;
	}

	public void setAddedToCart(boolean isAddedToCart) {
		this.isAddedToCart = isAddedToCart;
	}

	public VegetableDTO(String name, int priceKg) {
		super();
		this.name = name;
		this.priceKg = priceKg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriceKg() {
		return priceKg;
	}

	public void setPriceKg(int priceKg) {
		this.priceKg = priceKg;
	}
	
	
}
