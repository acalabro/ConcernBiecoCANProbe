package it.cnr.isti.labsedc.glimpse.event;

public class GlimpseBaseEventCAN<T> extends GlimpseBaseEventGeneric<String> {

	private static final long serialVersionUID = 1L;

	public GlimpseBaseEventCAN(
			String canMessageValue,
			String sensorName,
			Long timeStamp,
			String canMessageName,
			boolean isException,
			String canID) {
		
		super(canMessageValue, sensorName, timeStamp, canMessageName, isException, canID);
	}
	
}