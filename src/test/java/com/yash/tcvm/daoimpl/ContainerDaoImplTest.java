package com.yash.tcvm.daoimpl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.enumeration.IngredientContainer;
import com.yash.tcvm.exception.AlreadyExistException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;

public class ContainerDaoImplTest {

	private ContainerDao containerDao;

	@Before
	public void init() {
		containerDao = new ContainerDaoImpl();
	}

	@Test(expected = FileNotFoundException.class)
	public void getContainers_shouldThrowException_whenJSONFileForContainerNotFoundInProvidedPath() throws FileNotFoundException, EmptyException {
		containerDao.getContainers();
	}
	
	@Test(expected = EmptyException.class)
	public void getContainers_shouldThrowException_whenJSONFileForContainerIsEmpty() throws FileNotFoundException, EmptyException {
		containerDao.getContainers();
	}
	
	@Test
	public void insertContainer_ShouldReturnOne_WhenContainerObjectIsGiven() throws EmptyException, FileNotFoundException, AlreadyExistException{
		Container container = new Container(Ingredient.SUGAR, IngredientContainer.SUGAR_CONTAINER.getMaxCapacity(), 8000);
		assertEquals(1, containerDao.insertContainer(container));
	}
	
	@Test
	public void getContainers_shouldReturnSizeOfContainersList_whenListOfContainerObjectAreInJSONFile() throws FileNotFoundException, EmptyException {
		assertEquals(1, containerDao.getContainers().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void insertContainer_shouldThrowException_WhenContainerObjectGivenIsNull() throws FileNotFoundException, EmptyException, AlreadyExistException {
		Container container = null;
		assertEquals(1, containerDao.insertContainer(container));
	}
	
	@Test(expected = AlreadyExistException.class)
	public void insertContainer_ShouldThrowException_WhenContainerObjectAlreadyExist() throws EmptyException, FileNotFoundException, AlreadyExistException{
		Container container = new Container(Ingredient.MILK, 10000, 10000);
		assertEquals(1, containerDao.insertContainer(container));
	}
	
	@Test
	public void getContainer_ShouldReturnContainerObject_WhenContainerExistForGivenIngredient() throws FileNotFoundException{
		assertTrue(containerDao.getContainer(Ingredient.MILK) != null);
	}
	
	@Test
	public void getContainer_ShouldReturnNull_WhenContainerDoesntExistForGivenIngredient() throws FileNotFoundException{
		assertTrue(containerDao.getContainer(Ingredient.COFFEE) == null);
	}
	
	@Test
	public void updateContainer_ShouldReturnOne_WhenContainerObjectIsGivenAndExistInContainerList() throws EmptyException, FileNotFoundException, AlreadyExistException{
		Container container = new Container(Ingredient.MILK, IngredientContainer.MILK_CONTAINER.getMaxCapacity(), 10000);
		assertEquals(1, containerDao.updateContainer(container));
	}
	
	@Test(expected = NullPointerException.class)
	public void updateContainer_shouldThrowException_WhenExistingContainerObjectGivenIsNull() throws FileNotFoundException, EmptyException, AlreadyExistException {
		Container container = null;
		containerDao.updateContainer(container);
	}
	
	@Test(expected = NullPointerException.class)
	public void updateContainer_ShouldThrowException_WhenContainerObjectIsGivenAndDoesntExistInContainerList() throws EmptyException, FileNotFoundException, AlreadyExistException{
		Container container = new Container(Ingredient.COFFEE, 1200, 12200);
		assertEquals(1, containerDao.updateContainer(container));
	}

}
