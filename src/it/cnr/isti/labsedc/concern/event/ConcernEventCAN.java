package it.cnr.isti.labsedc.concern.event;

public class ConcernEventCAN<T> extends ConcernSimpleEventGeneric<String> {

	private static final long serialVersionUID = 1L;

	public ConcernEventCAN(
			String canMessageValue,
			String sensorName,
			Long timeStamp,
			String canMessageName,
			boolean isException,
			String canID) {
		
		super(canMessageValue, sensorName, timeStamp, canMessageName, isException, canID);
	}
	
}