package com.yash.tcvm.exception;

public class ContainerUnderflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContainerUnderflowException(){
		
	}
	
	public ContainerUnderflowException(String errMsg){
		super(errMsg);
		
	}
}
