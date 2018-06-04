package com.yash.tcvm;

import com.yash.tcvm.exception.EmptyException;

public class Start {
	
	public static void main(String[] args) throws EmptyException {
		TCVM.getTCVM().start();
	}

}
