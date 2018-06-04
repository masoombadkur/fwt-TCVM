package com.yash.tcvm.builder;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.interfaces.AbstractDrinkBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.config.BlackCoffeeConfiguration;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

public class BlackCoffeeBuilder extends AbstractDrinkBuilder {
	
	private static Logger logger = Logger.getLogger(BlackCoffeeBuilder.class);

	public BlackCoffeeBuilder() {
		setDrinkConfigurer(BlackCoffeeConfiguration.getDrinkConfigurer());
	}

	@Override
	public void prepareDrink(Order order) throws ContainerUnderflowException, FileNotFoundException, EmptyException {
		logger.info("BlackCoffeeBuilder's prepareDrink() method starts");
		
		if (order.getDrink() == Drink.BLACK_COFFEE) {
			super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + Drink.BLACK_COFFEE + " and found " + order.getDrink());
		}
	}

	public static IDrinkBuilder getDrinkBuilder() {
		logger.info("BlackCoffeeBuilder's getDrinkBuilder() method starts");
		return new BlackCoffeeBuilder();
	}

}

