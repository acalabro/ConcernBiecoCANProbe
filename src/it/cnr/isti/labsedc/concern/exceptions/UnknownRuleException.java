package it.cnr.isti.labsedc.concern.exceptions;

public class UnknownRuleException extends Exception {

	private static final long serialVersionUID = -8325934056030280854L;

	public UnknownRuleException()
	{
		System.out.println("Unable to found a rule with provided ruleName");
	}
	
	
}
