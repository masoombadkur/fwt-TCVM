package com.yash.tcvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.yash.tcvm.builder.BlackCoffeeBuilder;
import com.yash.tcvm.builder.BlackTeaBuilder;
import com.yash.tcvm.builder.CoffeeBuilder;
import com.yash.tcvm.builder.TeaBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.literal.TCVMMenuConstants;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.service.DrinkService;
import com.yash.tcvm.service.ReportService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.DrinkServiceImpl;
import com.yash.tcvm.serviceimpl.ReportServiceImpl;
import com.yash.tcvm.util.FileUtil;

/**
 * This class interacts with different service classes.
 * @author masoom.badkur
 *
 */
public class TCVM {
	
	private static Logger logger = Logger.getLogger(TCVM.class);

	private Scanner scanner;

	private static TCVM tcvm;

	private ContainerDao containerDao;
	private ContainerService containerService;

	private ReportService reportService;

	private DrinkService drinkService;

	private TCVM() {
		scanner = new Scanner(System.in);

		containerDao = new ContainerDaoImpl();
		containerService = new ContainerServiceImpl(containerDao);

		reportService = new ReportServiceImpl();

		drinkService = new DrinkServiceImpl();
	}

	public static TCVM getTCVM() {
		tcvm = new TCVM();
		return tcvm;
	}

	public void start() throws EmptyException {
		logger.info("TCVM's start() method starts");
		
		List<String> menuOptions = null;
		String choice = null;

		do {

			try {
				menuOptions = getMenu(TCVMMenuConstants.MENU_FILE_PATH);
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("Menu file not found");
			}

			for (String option : menuOptions) {
				System.out.println(option);
			}

			int selectedMenuOption = getUserSelectedMenuOption();

			getOperationToBePerformedBasedOnMenu(selectedMenuOption);

			System.out.println("Do you want to continue: y/n");
			scanner.nextLine();
			choice = scanner.nextLine();

			if (!choice.equalsIgnoreCase("y")) {
				System.out.println("Thank you for using TCVM..");
			}

		} while (choice.equalsIgnoreCase("y"));

	}

	public List<String> getMenu(String filePath) throws FileNotFoundException, EmptyException {
		
		logger.info("TCVM's getMenu() method starts");

		checkIfFilePathIsNull(filePath);

		checkIfFilePathIsEmpty(filePath);

		File menuFile = new File(filePath);

		checkIfFileExists(menuFile);

		checkIfFileIsEmpty(menuFile);

		List<String> menuOptions = FileUtil.readFile(filePath);

		return menuOptions;
	}

	private void checkIfFileIsEmpty(File menuFile) throws EmptyException {
		if (menuFile.length() == 0) {
			throw new EmptyException("File cannot be empty");
		}
	}

	private void checkIfFileExists(File menuFile) throws FileNotFoundException {
		if (!menuFile.exists()) {
			throw new FileNotFoundException("File not found in given location");
		}
	}

	private void checkIfFilePathIsEmpty(String filePath) throws EmptyException {
		if (filePath.isEmpty()) {
			throw new EmptyException("File location cannot be empty");
		}
	}

	private void checkIfFilePathIsNull(String filePath) {
		if (filePath == null) {
			throw new NullPointerException("File location cannot be null");
		}
	}

	private int getUserSelectedMenuOption() {
		int selectedMenuOption = 0;
		System.out.println("Enter your choice: ");
		selectedMenuOption = scanner.nextInt();
		if (selectedMenuOption < 0 || selectedMenuOption > TCVMMenuConstants.MAX_NO_OF_MENU_OPTIONS) {
			System.out.println("Wrong choice!");
		}
		return selectedMenuOption;
	}

	private void getOperationToBePerformedBasedOnMenu(int selectedMenuOption) {
		switch (selectedMenuOption) {
		case TCVMMenuConstants.MAKE_COFFEE:
			makeCoffee();
			break;

		case TCVMMenuConstants.MAKE_TEA:
			makeTea();
			break;

		case TCVMMenuConstants.MAKE_BLACK_COFFEE:
			makeBlackCoffee();
			break;

		case TCVMMenuConstants.MAKE_BLACK_TEA:
			makeBlackTea();
			break;

		case TCVMMenuConstants.REFILL_CONTAINER:
			refillContainer();
			break;

		case TCVMMenuConstants.CHECK_TOTAL_SALE:
			checkTotalSale();
			break;

		case TCVMMenuConstants.CONTAINER_STATUS:
			containerStatus();
			break;

		case TCVMMenuConstants.SHOW_REPORTS:
			showReports();
			break;

		case TCVMMenuConstants.EXIT_TCVM:
			System.out.println("Thank you for using TCVM..");
			System.exit(0);
			break;

		default:
			break;
		}
	}

	private void makeCoffee() {
		logger.info("TCVM's makeCoffee() method starts");
		
		System.out.println("## PREPARING COFFEE ##");
		IDrinkBuilder drinkBuilder = CoffeeBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.COFFEE);
		System.out.println("## Coffee is ready! ##");
	}

	private void makeTea() {
		logger.info("TCVM's makeTea() method starts");
		
		System.out.println("## PREPARING TEA ##");
		IDrinkBuilder drinkBuilder = TeaBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.TEA);
		System.out.println("## Tea is ready! ##");
	}

	private void makeBlackCoffee() {
		logger.info("TCVM's makeBlackCoffee() method starts");
		
		System.out.println("## PREPARING BLACK COFFEE ##");
		IDrinkBuilder drinkBuilder = BlackCoffeeBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.BLACK_COFFEE);
		System.out.println("## Black coffee is ready! ##");
	}

	private void makeBlackTea() {
		logger.info("TCVM's makeBlackTea() method starts");
		
		System.out.println("## PREPARING Black TEA ##");
		IDrinkBuilder drinkBuilder = BlackTeaBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.BLACK_TEA);
		System.out.println("## Black Tea is ready! ##");
	}

	private void refillContainer() {
		logger.info("TCVM's refillContainer() method starts");
		
		System.out.println("## REFILLING CONTAINER ##");
		List<Container> containers = null;
		try {
			containers = containerService.getContainers();
		} catch (FileNotFoundException e) {
			System.out.println("Container's json file not found");
		} catch (EmptyException e) {
			System.out.println("Container's json file is empty");
		}
		for (Container container : containers) {
			System.out.println(container.getIngredient() + " Container --- Quantity required: "
					+ (container.getMaxCapacity() - container.getCurrentAvailability()));
			container.setCurrentAvailability(container.getMaxCapacity());
			try {
				containerService.updateContainer(container);
			} catch (FileNotFoundException e) {
				System.out.println("Container's json file not found");
			} catch (EmptyException e) {
				System.out.println("Container's json file is empty");
			}
		}
		System.out.println("\n");
		containerStatus();
	}

	private void checkTotalSale() {
		logger.info("TCVM's checkTotalSale() method starts");
		
		System.out.println("## TOTAL SALE ##");
		try {
			System.out.println("Drink: " + Drink.TEA + " --- Total sale: Rs."
					+ drinkService.calculateSaleOfDrink(TeaBuilder.getDrinkBuilder(), Drink.TEA));
			System.out.println("Drink: " + Drink.COFFEE + " --- Total sale: Rs."
					+ drinkService.calculateSaleOfDrink(CoffeeBuilder.getDrinkBuilder(), Drink.COFFEE));
			System.out.println("Drink: " + Drink.BLACK_TEA + " --- Total sale: Rs."
					+ drinkService.calculateSaleOfDrink(BlackTeaBuilder.getDrinkBuilder(), Drink.BLACK_TEA));
			System.out.println("Drink: " + Drink.BLACK_COFFEE + " --- Total sale: Rs."
					+ drinkService.calculateSaleOfDrink(BlackCoffeeBuilder.getDrinkBuilder(), Drink.BLACK_COFFEE));
		} catch (FileNotFoundException e) {
			System.out.println("Order's json file not found");
		} catch (EmptyException e) {
			System.out.println("Order's json file is empty");
		}
	}

	private void containerStatus() {
		logger.info("TCVM's containerStatus() method starts");
		
		System.out.println("## CONTAINERS STATUS ##");
		List<Container> containers = null;
		try {
			containers = containerService.getContainers();
		} catch (FileNotFoundException e) {
			System.out.println("Container's json file not found");
		} catch (EmptyException e) {
			System.out.println("Container's json file is empty");
		}
		for (Container container : containers) {
			System.out.println(container.getIngredient() + " CONTAINER");
			System.out.println("Max capacity: " + container.getMaxCapacity());
			System.out.println("Current availability: " + container.getCurrentAvailability() + "\n");
		}
	}

	private void showReports() {
		logger.info("TCVM's showReports() method starts");
		
		System.out.println("## DISPLAYING REPORTS ##\nRefer folder src/main/resources/excel_sheets");
		System.out.println("1. Container Status Report");
		System.out.println("1. Total Tea-Coffee Sale Report Drink Wise");

		try {
			reportService.generateContainerStatusReport();
			reportService.generateDrinkWiseCupCostReport();
		} catch (FileNotFoundException e) {
			System.out.println("Required JSON file not found");
		} catch (EmptyException e) {
			System.out.println("Required JSON file is empty");
		} catch (IOException e) {
			System.out.println("Something went wrong while creating EXCEL sheet");
		}
	}

	private void makeDrink(IDrinkBuilder drinkBuilder, Drink drink) {
		logger.info("TCVM's makeDrink() method starts");
		
		System.out.println("Enter no of cups: ");
		int qtyOrdered = scanner.nextInt();

		Order order = new Order();
		order.setDrink(drink);
		order.setQuantity(qtyOrdered);

		try {
			drinkBuilder.prepareDrink(order);
		} catch (FileNotFoundException e) {
			System.out.println("Required JSON file not found");
		} catch (ContainerUnderflowException e) {
			System.out.println("Not enough ingredient in any of the required container");
		} catch (EmptyException e) {
			System.out.println("Required JSON file is empty");
		}
	}

}
