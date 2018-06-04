package com.yash.tcvm.builder.interfaces;

import java.io.FileNotFoundException;
import java.util.Map;

import com.yash.tcvm.config.interfaces.AbstractDrinkConfigurer;
import com.yash.tcvm.config.interfaces.IDrinkConfigurer;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.service.OrderService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;

public abstract class AbstractDrinkBuilder implements IDrinkBuilder {

	IDrinkConfigurer drinkConfigurer;

	ContainerDao containerDao = new ContainerDaoImpl();
	ContainerService containerService = new ContainerServiceImpl(containerDao);

	OrderDao orderDao = new OrderDaoImpl();
	OrderService orderService = new OrderServiceImpl(orderDao);

	public void setDrinkConfigurer(IDrinkConfigurer drinkConfigurer) {
		this.drinkConfigurer = drinkConfigurer;
	}

	public void prepareDrink(Order order) throws ContainerUnderflowException, FileNotFoundException, EmptyException {
		checkUnderFlow(order);
		updateContainers(order);
		placeOrder(order);
	}

	private void checkUnderFlow(Order order) throws FileNotFoundException, ContainerUnderflowException {
		AbstractDrinkConfigurer abstractDrinkConfigurer = (AbstractDrinkConfigurer) drinkConfigurer;

		Map<Ingredient, Double> consumption = abstractDrinkConfigurer.getIngredientConsumption();
		Map<Ingredient, Double> wastage = abstractDrinkConfigurer.getIngredientWastage();

		for (Map.Entry<Ingredient, Double> entry : consumption.entrySet()) {

			double qtyWasted = wastage.get(entry.getKey());
			double qtyConsumed = entry.getValue();
			double qtyAvailableInContainer = containerService.getContainer(entry.getKey()).getCurrentAvailability();
			int qtyOrdered = order.getQuantity();

			if (isUnderFlowCondition(qtyWasted, qtyConsumed, qtyAvailableInContainer, qtyOrdered)) {
				throw new ContainerUnderflowException(entry.getKey() + " insufficient");
			}
		}
	}

	private boolean isUnderFlowCondition(double qtyWasted, double qtyConsumed, double qtyAvailableInContainer,
			int qtyOrdered) {
		return (qtyOrdered * (qtyConsumed + qtyWasted)) > qtyAvailableInContainer;
	}

	private void updateContainers(Order order) throws FileNotFoundException, EmptyException {

		AbstractDrinkConfigurer abstractDrinkConfigurer = (AbstractDrinkConfigurer) drinkConfigurer;

		Map<Ingredient, Double> consumption = abstractDrinkConfigurer.getIngredientConsumption();
		Map<Ingredient, Double> wastage = abstractDrinkConfigurer.getIngredientWastage();

		for (Map.Entry<Ingredient, Double> entry : consumption.entrySet()) {

			Container container = containerService.getContainer(entry.getKey());

			double qtyWasted = wastage.get(entry.getKey());
			double qtyConsumed = entry.getValue();
			double qtyAvailableInContainer = container.getCurrentAvailability();
			int qtyOrdered = order.getQuantity();

			container.setCurrentAvailability(qtyAvailableInContainer - (qtyOrdered * (qtyConsumed + qtyWasted)));
			containerService.updateContainer(container);
		}

	}

	private void placeOrder(Order order) throws FileNotFoundException, EmptyException {
		order.setStatus(true);
		orderService.addOrder(order);
	}

	public double getDrinkRate() {
		AbstractDrinkConfigurer abstractDrinkConfigurer = (AbstractDrinkConfigurer) drinkConfigurer;
		return abstractDrinkConfigurer.getDrinkRate();
	}

}
