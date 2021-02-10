package it.cnr.isti.labsedc.concern.probe;

import it.cnr.isti.labsedc.concern.event.ConcernAbstractEvent;

public interface GlimpseProbeInterface {

	public void sendMessage(ConcernAbstractEvent<?> event, boolean debug);
}
