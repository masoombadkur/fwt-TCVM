package com.yash.tcvm.serviceimpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.BlackCoffeeBuilder;
import com.yash.tcvm.builder.BlackTeaBuilder;
import com.yash.tcvm.builder.CoffeeBuilder;
import com.yash.tcvm.builder.TeaBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.literal.ExcelSheetConstants;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.service.OrderService;
import com.yash.tcvm.service.ReportService;
import com.yash.tcvm.util.ExcelUtil;

public class ReportServiceImpl implements ReportService {
	
	private static Logger logger = Logger.getLogger(ReportServiceImpl.class);

	private ContainerDao containerDao;
	private ContainerService containerService;

	private OrderDao orderDao;
	private OrderService orderService;

	public ReportServiceImpl() {
		containerDao = new ContainerDaoImpl();
		containerService = new ContainerServiceImpl(containerDao);

		orderDao = new OrderDaoImpl();
		orderService = new OrderServiceImpl(orderDao);
	}

	public void generateContainerStatusReport() throws EmptyException, IOException {
		logger.info("ReportServiceImpl's generateContainerStatusReport() method starts");
		
		List<String> columns = new ArrayList<String>();
		columns.add("Ingredient");
		columns.add("Maximum capacity");
		columns.add("Current Availability");

		List<Container> containers = containerService.getContainers();
		List<Object[]> data = new ArrayList<>();
		for (Container container : containers) {
			Object[] datum = new Object[3];
			datum[0] = container.getIngredient();
			datum[1] = container.getMaxCapacity();
			datum[2] = container.getCurrentAvailability();
			data.add(datum);
		}

		ExcelUtil.createExcelSheet(columns, "Container's status", ExcelSheetConstants.CONTAINER_STATUS_REPORT, data);
		System.out.println(ExcelSheetConstants.CONTAINER_STATUS_REPORT + " generated successfully!");
	}

	public void generateDrinkWiseCupCostReport() throws EmptyException, IOException {
		logger.info("ReportServiceImpl's generateDrinkWiseCupCostReport() method starts");
		
		List<String> columns = new ArrayList<String>();
		columns.add("Drink");
		columns.add("Price Each");
		columns.add("Quantity Sold");
		columns.add("Total sale");

		List<Object[]> data = new ArrayList<>();

		data.add(getDrinkWiseData(TeaBuilder.getDrinkBuilder(), Drink.TEA));
		data.add(getDrinkWiseData(CoffeeBuilder.getDrinkBuilder(), Drink.COFFEE));
		data.add(getDrinkWiseData(BlackTeaBuilder.getDrinkBuilder(), Drink.BLACK_TEA));
		data.add(getDrinkWiseData(BlackCoffeeBuilder.getDrinkBuilder(), Drink.BLACK_COFFEE));

		ExcelUtil.createExcelSheet(columns, "Cup Cost Report", ExcelSheetConstants.CUP_COST_DRINK_WISE_REPORT, data);
		System.out.println(ExcelSheetConstants.CUP_COST_DRINK_WISE_REPORT + " generated successfully!");
	}

	private Object[] getDrinkWiseData(IDrinkBuilder drinkBuilder, Drink drink)
			throws FileNotFoundException, EmptyException {
		logger.info("ReportServiceImpl's getDrinkWiseData() method starts");
		
		List<Order> orders = null;
		orders = orderService.getOrdersByDrink(drink);

		double price = drinkBuilder.getDrinkRate();
		int qtySold = 0;
		double totalSale = 0;

		for (Order order : orders) {
			qtySold = qtySold + order.getQuantity();
			totalSale = totalSale + (order.getQuantity() * price);
		}

		Object[] datum = new Object[4];
		datum[0] = drink;
		datum[1] = price;
		datum[2] = qtySold;
		datum[3] = totalSale;

		return datum;
	}

}
