package iot.challenge.mira.backend.examples;

import java.util.UUID;

import com.eclipsesource.json.JsonObject;

public class ObtainDIW {

	private static final int IP = 0;
	private static final int DEVICE = 1;
	private static final int PASSWORD = 2;

	// Device with transactions
	// BE:AC:01:00:00:00
	public static void main(String[] args) {
		if (args.length != 3) {
			help();
		} else {
			execute(args[IP], args[DEVICE], args[PASSWORD]);
		}
	}

	private static void help() {
		System.out.println("Invalid arguments\nArgs: IP DEVICE PASSWORD");
	}

	private static void execute(String ip, String device, String password) {
		String seed = UUID.randomUUID().toString();
		
		JsonObject query = new JsonObject();
		query.add("device", device);
		query.add("seed", seed);
		query.add("value", SHA256.digest(device + password + seed));

		JsonObject result = Helper.post("http://" + ip + "/firma/diw", query);
		System.out.println(result);
	}
}
