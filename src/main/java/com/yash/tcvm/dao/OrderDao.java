package com.yash.tcvm.dao;

import java.io.FileNotFoundException;
import java.util.List;

import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

public interface OrderDao {

	List<Order> getOrders() throws FileNotFoundException, EmptyException;
	
	int insertOrder(Order order) throws EmptyException, FileNotFoundException;

	List<Order> getOrdersByDrink(Drink drink) throws FileNotFoundException;

}
