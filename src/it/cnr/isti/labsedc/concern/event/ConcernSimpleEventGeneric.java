package it.cnr.isti.labsedc.concern.event;

public class ConcernSimpleEventGeneric<T> extends ConcernSimpleEventAbstract<T> {

	private static final long serialVersionUID = 8661064245254655648L;
	private String probeID;	
	private String extraDataField;
	
	public ConcernSimpleEventGeneric(T data, String probeID, Long timeStamp, String eventName,
			boolean isException, String extraDataField) {
		
		super(data, timeStamp, eventName, isException);
		this.extraDataField = extraDataField;
		this.probeID = probeID;
		
	}
	public String getProbeID() {
		return probeID;
	}

	public void setProbeID(String probeID) {
		this.probeID = probeID;
	}

	public String getExtraDataField() {
		return extraDataField;
	}
	
	public void setExtraDataField(String extraDataField) {
		this.extraDataField = extraDataField;
	}
}
