package iot.challenge.mira.backend.examples.mqtt;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import iot.challenge.mira.backend.examples.Helper;

public class MqttInbox {

	private static final int IP = 0;
	private static final int MQTT_PASSWORD = 1;
	private static final int DEVICE = 2;
	private static final int DIW = 3;
	private static final int TRANSACTION_INDEX = 4;
	private static final int INBOX_ID = 5;

	private static MqttSubscriber subscriber;

	// Device with transactions
	// BE:AC:01:00:00:00
	public static void main(String[] args) {
		if (args.length != 6) {
			help();
		} else {
			execute(args[IP],
					args[MQTT_PASSWORD],
					args[DEVICE],
					args[DIW],
					Integer.parseInt(args[TRANSACTION_INDEX]),
					args[INBOX_ID]);
		}
	}

	private static void help() {
		System.out.println("Invalid arguments\nArgs: IP MQTT-PASSWORD DEVICE DIW TRANSACTION_INDEX INBOX_ID");
	}

	private static void execute(String ip, String pass, String device, String diw, int index, String inboxId) {

		subscribe(ip, inboxId, pass);

		JsonObject transaction = getTransaction(ip, device, diw, index);
		if (transaction != null) {
			System.out.println("This is the transaction: " + transaction);
			JsonObject message = getMessage(transaction);
			System.out.println("This is the message: " + message);

			share(message, inboxId);
		} else {
			subscriber.finish();
		}
	}

	private static void subscribe(String ip, String inboxId, String pass) {
		// MqttSubscriber -> MqttClient for a given topic
		subscriber = new MqttSubscriber("tcp://" + ip + ":1883", inboxId, pass, "/mira/inbox/" + inboxId, 2);
		subscriber.addListener(new JuraMqttListener() {
			@Override
			public void messageArrived(String topic, MqttMessage message) {
				JsonObject jsonMessage = JsonObject.readFrom(message.toString());
				System.out.println(inboxId + " has received: " + jsonMessage);
				String transaction = jsonMessage.get("transaction").asString();
				String key = jsonMessage.get("key").asString();
				System.out.println("Validate sign => " + validate(ip, transaction, key));
			}
		});

		subscriber.subscribe();
	}

	private static JsonObject getTransaction(String ip, String device, String diw, int index) {
		JsonObject query = new JsonObject();
		query.add("device", device);
		query.add("diw", diw);
		// ~index => index = 0

		JsonObject result = Helper.post("http://" + ip + "/firma/transactions", query);
		JsonArray transactions = result.get("transactions").asArray();
		return (transactions.size() >= index) ? transactions.asArray().get(index).asObject() : null;
	}

	private static JsonObject getMessage(JsonObject transaction) {
		JsonObject message = new JsonObject();
		message.add("transaction", transaction.get("transaction"));
		message.add("key", transaction.get("key"));

		// This is not necessary but...
		message.add("device", transaction.get("body").asObject().get("location").asObject().get("device"));

		return message;
	}

	private static void share(JsonObject message, String id) {
		try {
			MqttAsyncClient client = subscriber.getJuraMqttClient().getClient();
			byte[] payload = message.toString().getBytes();

			System.out.print("With a false address => ");
			client.publish("/mira/inbox/bous", payload, 2, false);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("nothing happens");

			System.out.print("With the inbox address => ");
			client.publish("/mira/inbox/" + id, payload, 2, false);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Beautiful!");

		} catch (Exception e) {
			e.printStackTrace();
			subscriber.finish();
		}
		subscriber.finish();
	}

	public static JsonObject validate(String ip, String transaction, String key) {
		JsonObject query = new JsonObject();
		query.add("transaction", transaction);
		query.add("key", key);
		query.add("mode", "anonymously");

		return Helper.post("http://" + ip + "/firma/validate", query);
	}
}
