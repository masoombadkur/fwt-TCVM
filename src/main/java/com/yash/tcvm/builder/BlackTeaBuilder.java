package com.yash.tcvm.builder;

import java.io.FileNotFoundException;

import com.yash.tcvm.builder.interfaces.AbstractDrinkBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.config.BlackTeaConfiguration;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

public class BlackTeaBuilder extends AbstractDrinkBuilder {

	public BlackTeaBuilder() {
		setDrinkConfigurer(BlackTeaConfiguration.getDrinkConfigurer());
	}

	@Override
	public void prepareDrink(Order order) throws ContainerUnderflowException, FileNotFoundException, EmptyException {
		if (order.getDrink() == Drink.BLACK_TEA) {
			super.prepareDrink(order);
		} else {
			throw new IllegalArgumentException(
					"Wrong Drink Type, required " + Drink.BLACK_TEA + " and found " + order.getDrink());
		}
	}

	public static IDrinkBuilder getDrinkBuilder() {
		return new BlackTeaBuilder();
	}

}
