package com.yash.tcvm.serviceimpl;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.DrinkService;
import com.yash.tcvm.service.OrderService;

public class DrinkServiceImpl implements DrinkService {
	
	private static Logger logger = Logger.getLogger(DrinkServiceImpl.class);

	private OrderDao orderDao;
	private OrderService orderService;

	public DrinkServiceImpl() {
		orderDao = new OrderDaoImpl();
		orderService = new OrderServiceImpl(orderDao);
	}

	public double calculateSaleOfDrink(IDrinkBuilder drinkBuilder, Drink drink)
			throws FileNotFoundException, EmptyException {
		logger.info("DrinkServiceImpl's calculateSaleOfDrink() method starts");
		
		double price = drinkBuilder.getDrinkRate();
		double totalSale = 0;
		List<Order> orders = orderService.getOrdersByDrink(drink);
		for (Order order : orders) {
			totalSale = totalSale + (order.getQuantity() * price);
		}
		return totalSale;
	}

}
