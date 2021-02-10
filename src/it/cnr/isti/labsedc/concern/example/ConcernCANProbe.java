package it.cnr.isti.labsedc.concern.example;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Random;

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labsedc.concern.event.ConcernEventCAN;
import it.cnr.isti.labsedc.concern.event.ConcernAbstractEvent;
import it.cnr.isti.labsedc.concern.probe.ConcernAbstractProbe;
import it.cnr.isti.labsedc.concern.utils.DebugMessages;
import it.cnr.isti.labsedc.concern.utils.Manager;

public class ConcernCANProbe extends ConcernAbstractProbe {

	/**
	 * This class provides an example of how to send messages (events) to Concern CEP.
	 * @author Antonello Calabr&ograve;
	 * @version 0.0.1
	 *
	 */

	public static int sendingInterval = 10000;
	public static String parameterName;
	public static float parameterValue;
	public static String sensorName;
	public static Random rand = new Random();

	public ConcernCANProbe(Properties settings) {
		super(settings);
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException {

		//creating a probe
		DebugMessages.line();
		ConcernCANProbe aGenericProbe = new ConcernCANProbe(
				Manager.createProbeSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
							  "tcp://127.0.0.1:61616","system", "manager","TopicCF",
								"jms.probeTopic",
								false,
								"probeName",
								"it.cnr.isti.labsedc.concern,java.lang,javax.security,java.util",
								"/home/acalabro/workspace/ConcernBiecoCanProbe/probe.ks",
								"/home/acalabro/workspace/ConcernBiecoCanProbe/probe.ts",
								"vera", "griselda"));

		//sending events
		try {
			int i = 0;
			while (i < 1000000) {
			aGenericProbe.generateAndSendExample_ConcernBaseEvents_CanBusPayload();
			Thread.sleep(5000);
			i++;
			}
		} catch (IndexOutOfBoundsException e) {}
	}

	public static double getProcessCpuLoad() {

		OperatingSystemMXBean sysInfo = ManagementFactory.getOperatingSystemMXBean();
		return sysInfo.getSystemLoadAverage();
	}

	@Override
	public void sendMessage(ConcernAbstractEvent<?> event, boolean debug) {
	}

	private void generateAndSendExample_ConcernBaseEvents_CanBusPayload() {

		DebugMessages.ok();
		DebugMessages.print(System.currentTimeMillis(), ConcernCANProbe.class.getSimpleName(),"Creating ConcernBiecoCanProbe message");
		ConcernEventCAN<String> message;
		DebugMessages.ok();
		DebugMessages.line();

		message = new ConcernEventCAN<String>("canMess", "CanBusArduinoSensor",
						System.currentTimeMillis(),	"Canbus Message",	"chceksum", "CANID");

			try {
				this.sendEventMessage(message, false);
				DebugMessages.println(System.currentTimeMillis(),
						ConcernCANProbe.class.getSimpleName(),
					"ConcernEventCAN message sent: {\n"
							+ "senderID: " + message.getSenderID() + "\n"
							+ "canID: " + message.getCanID() + "\n"
							+ "parameterValue: " + message.getEventData() + "\n"
							+ "timestamp: " + message.getTimestamp() + "\n"
							+ "checksum: " + message.getChecksum() + "\n"
							+"}");
				DebugMessages.line();
			} catch (JMSException | NamingException e) {
				e.printStackTrace();
			}
		}
}