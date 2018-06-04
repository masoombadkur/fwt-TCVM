package com.yash.tcvm.serviceimpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.service.ContainerService;

@RunWith(MockitoJUnitRunner.class)
public class ContainerServiceImplTest {

	@Mock
	private ContainerDao containerDao;

	private ContainerService containerService;

	private Container container;

	@Before
	public void init() {
		containerService = new ContainerServiceImpl(containerDao);
	}

	@Test
	public void getContainers_ShouldReturnSizeOfContainersList() throws FileNotFoundException, EmptyException {
		List<Container> containers = new ArrayList<>();
		containers.add(new Container(Ingredient.MILK, 12000, 12500));
		when(containerDao.getContainers()).thenReturn(containers);
		assertEquals(1, containerService.getContainers().size());
	}

	@Test(expected = NullPointerException.class)
	public void getContainers_ShouldThrowNullException_WhenListReturnedIsNull()
			throws FileNotFoundException, EmptyException {
		List<Container> containers = null;
		when(containerDao.getContainers()).thenReturn(containers);
		containerService.getContainers();
	}

	@Test(expected = EmptyException.class)
	public void getContainers_ShouldThrowEmptyException_WhenListReturnedIsEmpty()
			throws FileNotFoundException, EmptyException {
		List<Container> containers = new ArrayList<>();
		when(containerDao.getContainers()).thenReturn(containers);
		containerService.getContainers();
	}

	@Test
	public void getContainer_ShouldReturnContainerObject_WhenContainerIngredientIsGivenAndContainerMustPresentInFile()
			throws FileNotFoundException {
		container = new Container(Ingredient.MILK, 10000, 15000);
		when(containerDao.getContainer(Ingredient.MILK)).thenReturn(container);
		assertTrue(containerService.getContainer(Ingredient.MILK) != null);
	}

	@Test(expected = NullPointerException.class)
	public void getContainer_ShouldThrowException_WhenContainerIngredientIsNull() throws FileNotFoundException {
		containerService.getContainer(null);
	}

	@Test
	public void updateContainer_ShouldReturnOne_WhenContainerObjectIsGivenAndContainerIsPresentInFile()
			throws FileNotFoundException, EmptyException {
		container = new Container(Ingredient.MILK, 10000, 15000);
		when(containerDao.updateContainer(container)).thenReturn(1);
		assertEquals(1, containerService.updateContainer(container));
	}

	@Test(expected = NullPointerException.class)
	public void updateContainer_ShouldThrowException_WhenContainerObjectIsNull()
			throws FileNotFoundException, EmptyException {
		container = null;
		when(containerDao.updateContainer(container)).thenReturn(1);
		containerService.updateContainer(container);
	}
}
