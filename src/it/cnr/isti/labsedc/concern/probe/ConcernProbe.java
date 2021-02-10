package it.cnr.isti.labsedc.concern.probe;

import it.cnr.isti.labsedc.concern.event.ConcernAbstractEvent;

public interface ConcernProbe {

	void sendMessage(ConcernAbstractEvent<?> event, boolean debug);
}
