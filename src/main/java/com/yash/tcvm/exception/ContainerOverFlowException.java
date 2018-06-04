package com.yash.tcvm.exception;

public class ContainerOverFlowException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ContainerOverFlowException(){
		
	}
	
	public ContainerOverFlowException(String errMsg){
		super(errMsg);
	}

}
