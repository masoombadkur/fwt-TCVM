package com.yash.tcvm.builder;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.interfaces.AbstractDrinkBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.config.CoffeeConfiguration;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

public class CoffeeBuilder extends AbstractDrinkBuilder {
	
	private static Logger logger = Logger.getLogger(TeaBuilder.class);

	public CoffeeBuilder() {
		setDrinkConfigurer(CoffeeConfiguration.getDrinkConfigurer());
	}

	@Override
	public void prepareDrink(Order order) throws ContainerUnderflowException, FileNotFoundException, EmptyException {
		logger.info("CoffeeBuilder's prepareDrink() method starts");
		
		if (order.getDrink() == Drink.COFFEE) {
			super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + Drink.COFFEE + " and found " + order.getDrink());
		}
	}

	public static IDrinkBuilder getDrinkBuilder() {
		logger.info("CoffeeBuilder's getDrinkBuilder() method starts");
		return new CoffeeBuilder();
	}

}

