package com.yash.tcvm;

import com.yash.tcvm.exception.EmptyException;

/**
 * This class launch the application and calls the TCVM method to get menu.
 * 
 * @author masoom.badkur
 *
 */
public class Start {

	public static void main(String[] args) throws EmptyException {
		TCVM.getTCVM().start();
	}

}
