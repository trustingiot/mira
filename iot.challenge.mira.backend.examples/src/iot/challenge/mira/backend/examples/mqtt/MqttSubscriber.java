package iot.challenge.mira.backend.examples.mqtt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttSubscriber {

	protected static final String MQTT_PROP_CLIENT_ID = "client-id";
	protected static final String MQTT_PROP_BROKER_URL = "broker-url";
	protected static final String MQTT_PROP_KEEP_ALIVE = "keep-alive";
	protected static final String MQTT_PROP_CONNECTION_TIMEOUT = "connection-timeout";
	protected static final String MQTT_PROP_CLEAN_SESSION = "clean-session";
	protected static final String MQTT_PROP_USERNAME = "username";
	protected static final String MQTT_PROP_PASSWORD = "password";
	protected static final String MQTT_PROP_MQTT_VERSION = "mqtt-version";

	protected final String brokerUrl;

	protected final String id;

	protected final String password;

	protected String[] topics;

	protected int[] qos;

	protected Map<String, Object> properties;

	protected JuraMqttClient juraMqttClient;

	protected static Set<String> ids = new HashSet<>();

	private MqttSubscriber(String brokerUrl, String id, String password) {
		super();
		this.brokerUrl = brokerUrl;
		this.id = id;
		this.password = password;
	}

	public MqttSubscriber(String brokerUrl, String id, String password, String[] topics, int[] qos) {
		this(brokerUrl, id, password);
		this.topics = topics;
		this.qos = qos;
		connect();
	}

	public MqttSubscriber(String brokerUrl, String id, String password, String topic, int qos) {
		this(brokerUrl, id, password, new String[] { topic }, new int[] { qos });
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public String getPassword() {
		return password;
	}

	public String[] getTopics() {
		return topics;
	}

	public int[] getQos() {
		return qos;
	}

	public JuraMqttClient getJuraMqttClient() {
		return juraMqttClient;
	}

	/**
	 * Performs connection. It's called by the constructor
	 */
	protected void connect() {
		readProperties();
		juraMqttClient = createClient();
	}

	/**
	 * Starts the subscription
	 */
	public void subscribe() {
		if (isConnected()) {
			try {
				getJuraMqttClient().getClient().subscribe(topics, qos);
			} catch (MqttException e) {
				e.printStackTrace();
				finish();
			}
		}
	}

	/**
	 * Generates the 'properties' map
	 */
	protected void readProperties() {
		properties = new HashMap<>();
		properties.put(MQTT_PROP_CLIENT_ID, id);
		properties.put(MQTT_PROP_USERNAME, "jura");
		properties.put(MQTT_PROP_BROKER_URL, brokerUrl);
		properties.put(MQTT_PROP_KEEP_ALIVE, 30);
		properties.put(MQTT_PROP_CONNECTION_TIMEOUT, 30);
		properties.put(MQTT_PROP_CLEAN_SESSION, true);
		properties.put(MQTT_PROP_PASSWORD, password.toCharArray());
		properties.put(MQTT_PROP_MQTT_VERSION, MqttConnectOptions.MQTT_VERSION_3_1_1);
	}

	/**
	 * Creates MQTT client
	 *
	 * @return Created client
	 */
	protected JuraMqttClient createClient() {
		return new JuraMqttClient(
				read(MQTT_PROP_BROKER_URL, String.class),
				read(MQTT_PROP_CLIENT_ID, String.class),
				buildMqttConnectOptions(),
				true);
	}

	/**
	 * Creates MQTT options for Paho
	 * 
	 * @return Options
	 */
	protected MqttConnectOptions buildMqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(read(MQTT_PROP_USERNAME, String.class));
		options.setPassword(read(MQTT_PROP_PASSWORD, char[].class));
		options.setKeepAliveInterval(read(MQTT_PROP_KEEP_ALIVE, Integer.class));
		options.setConnectionTimeout(read(MQTT_PROP_CONNECTION_TIMEOUT, Integer.class));
		options.setCleanSession(read(MQTT_PROP_CLEAN_SESSION, Boolean.class));
		options.setMqttVersion(read(MQTT_PROP_MQTT_VERSION, Integer.class));
		return options;
	}

	protected <T> T read(String key, Class<T> type) {
		return read(properties, key, type);
	}

	private static <T> T read(Map<String, Object> map, String key, Class<T> type) {
		Object result = map.get(key);
		return type.isInstance(result) ? type.cast(result) : null;
	}

	/**
	 * Disconnects MQTT client
	 */
	public void finish() {
		if (isConnected()) {
			juraMqttClient.disconnect();
		}
	}

	/**
	 * Checks if the MQTT client is connected
	 * 
	 * @return True connected, False otherwise
	 */
	public boolean isConnected() {
		return juraMqttClient != null && juraMqttClient.isConnected();
	}

	/**
	 * Adds 'listener' to MQTT client listeners
	 * 
	 * @param listener
	 *            Listener to be added
	 */
	public void addListener(JuraMqttListener listener) {
		if (juraMqttClient != null)
			juraMqttClient.addListener(listener);
	}

	/**
	 * Removes 'listener' from MQTT client listeners
	 * 
	 * @param listener
	 *            Listener to be removed
	 */
	public void removeListener(JuraMqttListener listener) {
		if (juraMqttClient != null)
			juraMqttClient.removeListener(listener);
	}
}
