package com.yash.tcvm;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import com.yash.tcvm.exception.EmptyException;

public class TCVMTest {
	
	private TCVM tcvm;
	
	@Before
	public void init() {
		tcvm = TCVM.getTCVM();
	}

	@Test(expected = NullPointerException.class)
	public void getMenu_shouldThrowNullException_whenFilePathIsNull() throws FileNotFoundException, EmptyException {
		tcvm.getMenu(null);
	}
	
	@Test(expected = EmptyException.class)
	public void getMenu_shouldThrowEmptyException_whenFilePathIsEmpty() throws FileNotFoundException, EmptyException {
		tcvm.getMenu("");
	}
	
	@Test(expected = FileNotFoundException.class)
	public void getMenu_shouldThrowNotFoundException_whenFileDoesnotExistInGivenPath() throws FileNotFoundException, EmptyException {
		tcvm.getMenu("src/test/resources/menu/menu_not_exist.txt");
	}
	
	@Test(expected = EmptyException.class)
	public void getMenu_shouldThrowEmptyException_whenFileIsEmpty() throws FileNotFoundException, EmptyException {
		tcvm.getMenu("src/test/resources/menu/menu_empty.txt");
	}
	
	@Test(expected = FileNotFoundException.class)
	public void getMenu_shouldThrowEmptyException_whenFilePathIsIncorrect() throws FileNotFoundException, EmptyException {
		tcvm.getMenu("src/resources/menu.txt");
	}
	
	@Test
	public void getMenu_shouldReturnNoOfLinesInFileAsList_whenFilePathIsGiven() throws FileNotFoundException, EmptyException {
		assertEquals(12, tcvm.getMenu("src/test/resources/menu/menu.txt").size());
	}
}
