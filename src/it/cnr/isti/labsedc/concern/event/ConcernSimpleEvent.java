package it.cnr.isti.labsedc.concern.event;

/**
 * The GlimpseBaseEvent <T> is the generic basic event that the monitoring infrastructure<br />
 * uses to infer more complex events. These object must be sent from a probe<br />
 * to GLIMPSE through the ESB.<br /><br />
 * 
 * The parameter of GlimpseBaseEvent(String, String, String, String, int, int, Long, String, boolean)<br />
 * are useful to provide information about the connector where the probe is instanciated.<br /><br />
 * Extending the GlimpseBaseEvent(String, String, String, String, int, int, Long, String, boolean) class<br />
 * it's possible to add or change variables and to manages it into the CEP using a well-formed Drools rule.<br />
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.4
 * 
 * @param <T> The type of the data on the payload of the GlimpseBaseEvent, see method {@link #getData()} and {@link #setData(Object)}
 */

public interface ConcernSimpleEvent <T> {
	
	public T getEventData();
	public void setEventData(T t);
		
	public Long getTimeStamp();
	public void setTimeStamp(Long timeStamp);
	
	public String getEventName();
	public void setEventName(String eventName);
	
	public boolean isException();
	public void setException(boolean isException);
	
	public boolean isConsumed();
	public void setConsumed(boolean consumed);
}