package com.yash.tcvm.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

	@Mock
	private OrderDao orderDao;

	private OrderService orderService;

	private Order order;

	@Before
	public void init() {
		orderService = new OrderServiceImpl(orderDao);
	}

	@Test
	public void getOrders_ShouldReturnSizeOfOrdersList() throws FileNotFoundException, EmptyException {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(3, Drink.TEA, true));
		when(orderDao.getOrders()).thenReturn(orders);
		assertEquals(1, orderService.getOrders().size());
	}

	@Test(expected = NullPointerException.class)
	public void getOrders_ShouldThrowNullException_WhenListReturnedIsNull()
			throws FileNotFoundException, EmptyException {
		List<Order> orders = null;
		when(orderDao.getOrders()).thenReturn(orders);
		orderService.getOrders();
	}

	@Test(expected = EmptyException.class)
	public void getOrders_ShouldThrowEmptyException_WhenListReturnedIsEmpty()
			throws FileNotFoundException, EmptyException {
		List<Order> orders = new ArrayList<>();
		when(orderDao.getOrders()).thenReturn(orders);
		orderService.getOrders();
	}

	@Test
	public void getOrdersByDrink_ShouldReturnSizeOfGivenDrinkOrdersList_WhenDrinkTypeIsGiven() throws FileNotFoundException, EmptyException {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(3, Drink.TEA, true));
		when(orderDao.getOrdersByDrink(Drink.TEA)).thenReturn(orders);
		assertEquals(1, orderService.getOrdersByDrink(Drink.TEA).size());
	}
	
	@Test(expected = NullPointerException.class)
	public void getOrdersByDrink_ShouldThrowNullException_WhenDrinkTypeIsGivenAndListReturnedIsNull()
			throws FileNotFoundException, EmptyException {
		List<Order> orders = null;
		when(orderDao.getOrdersByDrink(Drink.TEA)).thenReturn(orders);
		orderService.getOrdersByDrink(Drink.TEA);
	}

	@Test(expected = EmptyException.class)
	public void getOrdersByDrink_ShouldThrowEmptyException_WhenDrinkTypeIsGivenAndListReturnedIsEmpty()
			throws FileNotFoundException, EmptyException {
		List<Order> orders = new ArrayList<>();
		when(orderDao.getOrdersByDrink(Drink.TEA)).thenReturn(orders);
		orderService.getOrders();
	}

	@Test(expected = NullPointerException.class)
	public void addOrder_shouldThrowNullException_WhenOrderObjectIsNull() throws FileNotFoundException, EmptyException {
		order = null;
		orderService.addOrder(order);
	}

	@Test
	public void addOrder_ShouldReturnOne_WhenObjectObjectIsGiven() throws FileNotFoundException, EmptyException {
		order = new Order();
		when(orderDao.insertOrder(order)).thenReturn(1);
		assertEquals(1, orderService.addOrder(order));
	}

}
