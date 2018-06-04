package com.yash.tcvm.builder;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.interfaces.AbstractDrinkBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.config.TeaConfiguration;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

public class TeaBuilder extends AbstractDrinkBuilder {
	
	private static Logger logger = Logger.getLogger(TeaBuilder.class);

	public TeaBuilder() {
		setDrinkConfigurer(TeaConfiguration.getDrinkConfigurer());
	}

	@Override
	public void prepareDrink(Order order) throws ContainerUnderflowException, FileNotFoundException, EmptyException {
		logger.info("TeaBuilder's prepareDrink() method starts");
		
		if (order.getDrink() == Drink.TEA) {
			super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + Drink.TEA + " and found " + order.getDrink());
		}
	}

	public static IDrinkBuilder getDrinkBuilder() {
		logger.info("TeaBuilder's getDrinkBuilder() method starts");
		return new TeaBuilder();
	}

}
