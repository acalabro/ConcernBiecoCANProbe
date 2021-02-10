package it.cnr.isti.labsedc.concern.event;

import it.cnr.isti.labsedc.concern.cep.CepType;

public class ConcernEventCAN<T> extends ConcernAbstractEvent<String> {

	private static final long serialVersionUID = 1L;
	private String canID;

	public ConcernEventCAN(
			String canMessageValue,
			String sensorName,
			Long timeStamp,
			String canMessageName,
			String checkSum,
			String canID) {
		super(canMessageValue, CepType.DROOLS , sensorName, checkSum,timeStamp);
		this.canID = canID;
	}
	
	public void setCanID(String canID)
	{
		this.canID=canID;
	}
	
	public String getCanID()
	{
		return canID;
	}
}