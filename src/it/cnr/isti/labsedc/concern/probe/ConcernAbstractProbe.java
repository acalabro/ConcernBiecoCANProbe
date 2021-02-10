package it.cnr.isti.labsedc.concern.probe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;

import it.cnr.isti.labsedc.concern.event.ConcernAbstractEvent;
import it.cnr.isti.labsedc.concern.utils.DebugMessages;
import it.cnr.isti.labsedc.concern.utils.Manager;

public abstract class ConcernAbstractProbe implements ConcernProbe {

	protected static InitialContext initContext;
	protected static TopicSession publishSession;
	protected static TopicPublisher tPub;
	protected static TopicConnection connection;
	protected static Topic connectionTopic;
	protected static int MESSAGEID = 0;
	protected boolean retry = true;
	protected int seconds = 5;

	/**
	 * This constructor allow to create a GlimpseAbstractProbe object<br />
	 * providing the {@link Properties} settings object
	 * @param settings can be generated automatically
	 * using {@link Manager#createConsumerSettingsPropertiesObject(String, String, String, String, String, String, boolean, String)}.
	 * @throws InterruptedException
	 *
	 */
	public ConcernAbstractProbe(Properties settings) {

		while (retry) {
			try {
				initContext = this.initConnection(settings, true);
				if (settings.getProperty("java.naming.provider.url").startsWith("ssl")) {
					this.createSSLConnection(initContext,settings.getProperty("topic.probeTopic").replaceFirst("jms.", ""), settings, true);
				}
				else
					this.createConnection(initContext, settings, true);
				retry = false;
			} catch (NamingException | JMSException e) {
				DebugMessages.fail();
				DebugMessages.line();
				DebugMessages.println(System.currentTimeMillis(), this.getClass().getSimpleName(), "CONNECTION FAILED - Retrying in " + seconds + " seconds");
				if (seconds <300) {
					retry = true;
					DebugMessages.line();
				try {
					Thread.sleep(seconds *1000);
				} catch (InterruptedException e1) {
				}
				seconds +=5;
				DebugMessages.println(System.currentTimeMillis(), this.getClass().getSimpleName(), "Retrying .......... ");
				}

			}
		}
	}

	/**
	 * This method setup a {@link TopicConnection} object.
	 *
	 * @param initConn the InitialContext object generated using the method {@link #initConnection(Properties, boolean)}.
	 * @param settings can be generated automatically using {@link Manager#createProbeSettingsPropertiesObject(String, String, String, String, String, String, boolean, String, String)}
	 * @param probeChannel
	 * @param settings
	 * @param debug
	 * @return a {@link TopicPublisher} object
	 * @throws NamingException
	 * @throws JMSException
	 */



	 protected TopicPublisher createSSLConnection(InitialContext initConn, String probeTopic, Properties settings, boolean debug) throws NamingException, JMSException
		{
			if (debug) {
				DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
						"Creating SSL ConnectionFactory with settings ");
				try {
				ActiveMQSslConnectionFactory connFact;


				System.setProperty("javax.net.ssl.keyStore", settings.getProperty("keyStore"));
			    System.setProperty("javax.net.ssl.keyStorePassword", settings.getProperty("keyStorePassword"));
			    System.setProperty("javax.net.debug", "handshake");

			    connFact = new ActiveMQSslConnectionFactory(settings.getProperty("java.naming.provider.url"));

				connFact.setTrustStore(settings.getProperty("trustStore"));
			    connFact.setTrustStorePassword(settings.getProperty("trustStorePassword"));
				connFact.setTrustAllPackages(true);
				connFact.setTrustedPackages(new ArrayList<String>(Arrays.asList(settings.getProperty("activemq.trustable.serializable.class.list").split(","))));


				DebugMessages.ok();
				DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating TopicConnection ");
				connection = connFact.createTopicConnection();
				if (debug) {
				DebugMessages.ok();
				DebugMessages.line(); }
				if (debug) {
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating Session "); }
				publishSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
				if (debug) {
					DebugMessages.ok();
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Looking up for channel ");}
				connectionTopic = (Topic) initContext.lookup(settings.getProperty("topic.probeTopic").replaceFirst("jms.", ""));
				if (debug) {
					DebugMessages.ok();
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating Publisher "); }
				return tPub = publishSession.createPublisher(connectionTopic);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
			return null;

		}

	 protected TopicPublisher createConnection(InitialContext initConn, Properties settings, boolean debug) throws NamingException, JMSException
		{
			if (debug) {
				DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
						"Creating ConnectionFactory with settings ");
				try {
				ActiveMQConnectionFactory connFact;

			    connFact = new ActiveMQConnectionFactory(settings.getProperty("java.naming.provider.url"));

				DebugMessages.ok();
				DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating TopicConnection ");
				connection = connFact.createTopicConnection();
				if (debug) {
				DebugMessages.ok();
				DebugMessages.line(); }
				if (debug) {
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating Session "); }
				publishSession = connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
				if (debug) {
					DebugMessages.ok();
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Looking up for channel ");}
				connectionTopic = (Topic) initContext.lookup(settings.getProperty("topic.probeTopic").replaceFirst("jms.", ""));
				if (debug) {
					DebugMessages.ok();
					DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
							"Creating Publisher "); }
				return tPub = publishSession.createPublisher(connectionTopic);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
			return null;

		}



	/**
	 * This method setup the connection parameters using the {@link Properties} object {@link #settings}
	 *
	 * @param settings the parameter to setup the connection to the Enterprise Service Bus<br /> to send messages
	 * @param debug
	 * @return it provides an {@link InitialContext} object that will be used<br />during the Consumer <-> Monitoring interaction.
	 * @throws NamingException
	 */
		protected InitialContext initConnection(Properties settings, boolean debug) throws NamingException {
			if (debug)
			DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
					"Creating InitialContext with settings ");
			InitialContext initConn = new InitialContext(settings);
			if (debug) {
				DebugMessages.ok();
				DebugMessages.line(); }
			return initConn;
		}


	public abstract void sendMessage(ConcernAbstractEvent<?> event, boolean debug);

	/**
	 * This method send a {@link ConcernSimpleEvent} message on the ESB<br />
	 * specifically on the channel specified in the {@link #settings} object.
	 *
	 * @param event the event to send
	 * @param debug
	 * @throws JMSException
	 * @throws NamingException
	 */
	protected void sendEventMessage(ConcernAbstractEvent<?> event, boolean debug) throws JMSException,
			NamingException {
		if (debug) {
			DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
					"Creating Message "); }
		try
		{
			ObjectMessage messageToSend = publishSession.createObjectMessage();
			messageToSend.setJMSMessageID(String.valueOf(MESSAGEID++));
			messageToSend.setObject(event);
		if (debug) {
			DebugMessages.ok();
			DebugMessages.print(System.currentTimeMillis(), this.getClass().getSimpleName(),
					"Publishing message  "); }
		tPub.publish(messageToSend);
		if (debug) {
			DebugMessages.ok();
			DebugMessages.line(); }
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
