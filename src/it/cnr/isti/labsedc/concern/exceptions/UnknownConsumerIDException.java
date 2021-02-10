package it.cnr.isti.labsedc.concern.exceptions;

public class UnknownConsumerIDException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownConsumerIDException()
	{
		System.out.println("Check request ID, may contains errors");
	}
}
