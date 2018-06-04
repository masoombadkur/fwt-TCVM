package com.yash.tcvm.serviceimpl;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;

import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	private OrderDao orderDao;

	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public List<Order> getOrders() throws FileNotFoundException, EmptyException {
		logger.info("OrderServiceImpl's getOrders() method starts");
		
		List<Order> orders = orderDao.getOrders();
		if (orders == null) {
			throw new NullPointerException("Order's list is null");
		}

		if (orders.isEmpty()) {
			throw new EmptyException("Order's list is empty");
		}
		return orders;
	}

	@Override
	public List<Order> getOrdersByDrink(Drink drink) throws FileNotFoundException, EmptyException {
		logger.info("OrderServiceImpl's getOrdersByDrink() method starts");
		
		List<Order> ordersListByDrink = orderDao.getOrdersByDrink(drink);
		if (ordersListByDrink == null) {
			throw new NullPointerException("Order's list for given drink is null");
		}

		if (ordersListByDrink.isEmpty()) {
			throw new EmptyException("Order's list for given drink is empty");
		}
		return ordersListByDrink;
	}

	@Override
	public int addOrder(Order order) throws FileNotFoundException, EmptyException {
		logger.info("OrderServiceImpl's addOrder() method starts");
		
		int rowsAffected = 0;
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
		rowsAffected = orderDao.insertOrder(order);
		return rowsAffected;
	}

}
