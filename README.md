GlimpseBiecoCanProbe
====================

Information   | Value
------------- | --------
Package       | it.cnr.isti.labsedc.glimpse
Roadmap       | TBD
Language      | Java

# Summary
A probe for sending events captured by arduino device connected to a car canbus.

# How it works?
Based on Publish Subscribe paradigm.
JMS messages through ActiveMQ.
Event correlation through Complex Event Processor by means of Drools Engine.

# Configuration
To configure the probe, setup the parameter within the method Manager.createProbeSettingsPropertiesObject
