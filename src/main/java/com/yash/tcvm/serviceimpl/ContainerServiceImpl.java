package com.yash.tcvm.serviceimpl;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.service.ContainerService;

public class ContainerServiceImpl implements ContainerService {
	
	private static Logger logger = Logger.getLogger(ContainerServiceImpl.class);

	private ContainerDao containerDao;

	public ContainerServiceImpl(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}

	@Override
	public List<Container> getContainers() throws FileNotFoundException, EmptyException {
		logger.info("ContainerServiceImpl's getContainers() method starts");
		
		List<Container> containers = containerDao.getContainers();
		if (containers == null) {
			throw new NullPointerException("Container's list is null");
		}

		if (containers.isEmpty()) {
			throw new EmptyException("Container's list is empty");
		}
		return containers;
	}

	@Override
	public Container getContainer(Ingredient ingredient) throws FileNotFoundException {
		logger.info("ContainerServiceImpl's getContainer() method starts");
		
		if (ingredient == null) {
			throw new NullPointerException("Ingredient is null");
		}
		Container container = containerDao.getContainer(ingredient);
		return container;
	}

	@Override
	public int updateContainer(Container container) throws FileNotFoundException, EmptyException {
		logger.info("ContainerServiceImpl's updateContainer() method starts");
		
		int rowsAffected = 0;
		if (container == null) {
			throw new NullPointerException("Container cannot be null");
		}
		rowsAffected = containerDao.updateContainer(container);
		return rowsAffected;
	}
}
