package com.yash.tcvm.enumeration;

public enum IngredientContainer {

	SUGAR_CONTAINER(8000), TEA_CONTAINER(2000), COFFEE_CONTAINER(2000), MILK_CONTAINER(10000), WATER_CONTAINER(15000);

	private double maxCapacity;

	IngredientContainer(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public double getMaxCapacity() {
		return maxCapacity;
	}

}
