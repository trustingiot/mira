package iot.challenge.mira.backend.examples.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Interface to be implemented by a listener of JuraMqttClient
 */
public interface JuraMqttListener {
	void messageArrived(String topic, MqttMessage message);
}