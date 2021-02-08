 /*
  * GLIMPSE: A generic and flexible monitoring infrastructure.

  * For further information: http://labsewiki.isti.cnr.it/labse/tools/glimpse/public/main
  *
  * Copyright (C) 2015  Software Engineering Laboratory - ISTI CNR - Pisa - Italy
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
*/
package it.cnr.isti.labsedc.glimpse.example;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Random;

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventCAN;
import it.cnr.isti.labsedc.glimpse.probe.GlimpseAbstractProbe;
import it.cnr.isti.labsedc.glimpse.utils.DebugMessages;
import it.cnr.isti.labsedc.glimpse.utils.Manager;

public class MyGlimpseBiecoCanProbe extends GlimpseAbstractProbe {

	/**
	 * This class provides an example of how to send messages (events) to Glimpse CEP.
	 * @author Antonello Calabr&ograve;
	 * @version 3.3.3
	 *
	 */

	public static int sendingInterval = 10000;
	public static String parameterName;
	public static float parameterValue;
	public static String roomID;
	public static String sensorName;
	public static Random rand = new Random();

	public MyGlimpseBiecoCanProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException {

		//creating a probe
		DebugMessages.line();
		MyGlimpseBiecoCanProbe aGenericProbe = new MyGlimpseBiecoCanProbe(
				Manager.createProbeSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
								//"ssl://146.48.77.37:61617","system", "manager","TopicCF",
							  "tcp://glimpse-dev.isti.cnr.it:61616","system", "manager","TopicCF",
								"jms.probeTopic",
								false,
								"probeName",
								"it.cnr.isti.labsedc.glimpse,java.lang,javax.security,java.util",
								"/home/acalabro/workspace/GlimpseBiecoCanProbe/probe.ks",
								"/home/acalabro/workspace/GlimpseBiecoCanProbe/probe.ts",
								"n1hehe", "n1hehe"));

		//sending events
		try {
			int i = 0;
			while (i < 1000000) {
			aGenericProbe.generateAndSendExample_GlimpseBaseEvents_CanBusPayload();
			i++;
			}
		} catch (IndexOutOfBoundsException e) {}
	}

	public static double getProcessCpuLoad() {

		OperatingSystemMXBean sysInfo = ManagementFactory.getOperatingSystemMXBean();
		return sysInfo.getSystemLoadAverage();
	}

	@Override
	public void sendMessage(GlimpseBaseEvent<?> event, boolean debug) {
	}

	private void generateAndSendExample_GlimpseBaseEvents_CanBusPayload() {

		DebugMessages.ok();
		DebugMessages.print(System.currentTimeMillis(), MyGlimpseBiecoCanProbe.class.getSimpleName(),"Creating GlimpseBaseEventSB message");
		GlimpseBaseEventCAN<String> message;
		DebugMessages.ok();
		DebugMessages.line();

		message = new GlimpseBaseEventCAN<String>("canMess", "CanBusArduinoSensor",
						System.currentTimeMillis(),	"Canbus Message",	false, "CANID");

			try {
				this.sendEventMessage(message, false);
				DebugMessages.println(System.currentTimeMillis(),
					MyGlimpseBiecoCanProbe.class.getSimpleName(),
					"GlimpseBaseEventSB message sent: {\n"
							+ "sensorName: " + message.getProbeID() + "\n"
							+ "parameterName: " + message.getEventName() + "\n"
							+ "parameterValue: " + message.getEventData() + "\n"
							+ "timestamp: " + message.getTimeStamp() + "\n"
							+ "canID: " + message.getExtraDataField() + "\n"
							+"}");
				DebugMessages.line();
			} catch (JMSException | NamingException e) {
				e.printStackTrace();
			}
		}
}