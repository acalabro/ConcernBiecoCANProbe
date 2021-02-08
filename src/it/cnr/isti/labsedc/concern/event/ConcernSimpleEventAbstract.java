
package it.cnr.isti.labsedc.concern.event;

import java.io.Serializable;

/**
 * 
 * This class is a possible implementation (extension) of the {@link ConcernSimpleEvent} class.<br /><br /> 
 * This implementation refer to a CONNECT project scenario.
 * 
 * @author Antonello Calabr&ograve;
 * @version 3.4
 */
public abstract class ConcernSimpleEventAbstract<T> implements ConcernSimpleEvent<T>, Serializable
{
	private static final long serialVersionUID = 431412878795449197L;
	protected T data;
	protected Long timeStamp;
	protected String eventName;
	protected boolean isException;
	private boolean consumed;

	public ConcernSimpleEventAbstract(T data, Long timeStamp,	String eventName, boolean isException) {
		
		this.data = data;
		this.timeStamp = timeStamp;
		this.eventName = eventName;
		this.isException = isException;
		this.consumed = false;
	}

	public T getEventData() {
		return data;
	}

	public void setEventData(T data) {
		this.data = data;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}
}
