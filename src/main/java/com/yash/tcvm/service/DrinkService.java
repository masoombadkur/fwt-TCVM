package com.yash.tcvm.service;

import java.io.FileNotFoundException;

import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;

public interface DrinkService {
	
	double calculateSaleOfDrink(IDrinkBuilder drinkBuilder, Drink drink) throws FileNotFoundException, EmptyException;

}
