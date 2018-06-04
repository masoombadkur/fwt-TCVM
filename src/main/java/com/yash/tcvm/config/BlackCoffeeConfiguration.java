package com.yash.tcvm.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yash.tcvm.config.interfaces.AbstractDrinkConfigurer;
import com.yash.tcvm.config.interfaces.IDrinkConfigurer;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.enumeration.Ingredient;

public class BlackCoffeeConfiguration extends AbstractDrinkConfigurer {
	
	private static Logger logger = Logger.getLogger(BlackCoffeeConfiguration.class);

	private static IDrinkConfigurer drinkConfigurer;

	private static final double WATER_CONSUMPTION = 100;
	private static final double SUGAR_CONSUMPTION = 15;
	private static final double COFFEE_CONSUMPTION = 3;

	private static final double WATER_WASTAGE = 12;
	private static final double SUGAR_WASTAGE = 2;
	private static final double COFFEE_WASTAGE = 0;

	private static final double RATE = 10;

	private BlackCoffeeConfiguration() {
		// TODO Auto-generated constructor stub
	}

	static {
		drinkConfigurer = new BlackCoffeeConfiguration();
	}
	
	public static IDrinkConfigurer getDrinkConfigurer() {
		return drinkConfigurer;
	}

	public void configIngredientConsumption() {
		logger.info("BlackCoffeeConfiguration's configIngredientConsumption() method starts");
		
		Map<Ingredient, Double> ingredientsConsumption = new HashMap<Ingredient, Double>();

		ingredientsConsumption.put(Ingredient.COFFEE, COFFEE_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.WATER, WATER_CONSUMPTION);
		ingredientsConsumption.put(Ingredient.SUGAR, SUGAR_CONSUMPTION);

		setIngredientConsumption(ingredientsConsumption);
	}

	public void configIngredientWastage() {
		logger.info("BlackCoffeeConfiguration's configIngredientWastage() method starts");
		
		Map<Ingredient, Double> ingredientsWastage = new HashMap<Ingredient, Double>();

		ingredientsWastage.put(Ingredient.COFFEE, COFFEE_WASTAGE);
		ingredientsWastage.put(Ingredient.WATER, WATER_WASTAGE);
		ingredientsWastage.put(Ingredient.SUGAR, SUGAR_WASTAGE);

		setIngredientWastage(ingredientsWastage);
	}

	public void configDrinkType() {
		logger.info("BlackCoffeeConfiguration's configDrinkType() method starts");
		setDrinkType(Drink.BLACK_COFFEE);
	}

	public void configDrinkRate() {
		logger.info("BlackCoffeeConfiguration's configDrinkRate() method starts");
		setDrinkRate(RATE);
	}

}
