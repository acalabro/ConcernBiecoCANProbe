package it.cnr.isti.labsedc.concern.utils;

public enum Status {
		/**
		 * STARTING - The monitoring is booting up
		 * ACTIVE - The monitoring is ready to accept incoming requests
		 * STOPPING - The monitoring is shutting down
		 * UNAVAILABLE - The monitoring is not available for evaluations
		 */
		STARTING, ACTIVE, STOPPING, UNAVAILABLE
}
