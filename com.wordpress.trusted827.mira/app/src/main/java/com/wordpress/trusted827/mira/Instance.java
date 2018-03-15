package com.wordpress.trusted827.mira;

// TODO set instance values
public interface Instance {
    String BACKEND_PROTOCOL = "http";
    String BACKEND_IP = "todo";
    String BACKEND_URL = BACKEND_PROTOCOL + "://" + BACKEND_IP;

    String MQTT_PROTOCOL = "tcp";
    String MQTT_SERVER = "todo";
    String MQTT_SERVER_URL = MQTT_PROTOCOL + "://" + MQTT_SERVER;

    String MQTT_USER = "todo";
    char[] MQTT_PASSWORD = "todo".toCharArray();

    String TRANSACTIONS_EXPLORER = "https://thetangle.org/transaction/";
}
