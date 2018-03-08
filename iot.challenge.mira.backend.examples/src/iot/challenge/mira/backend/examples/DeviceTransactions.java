package iot.challenge.mira.backend.examples;

import com.eclipsesource.json.JsonObject;

public class DeviceTransactions {

	private static final int IP = 0;
	private static final int DEVICE = 1;
	private static final int DIW = 2;
	private static final int INDEX = 3;

	// Device with transactions
	// BE:AC:01:00:00:00
	public static void main(String[] args) {
		if (args.length != 4) {
			help();
		} else {
			execute(args[IP], args[DEVICE], args[DIW], Integer.parseInt(args[INDEX]));
		}
	}

	private static void help() {
		System.out.println("Invalid arguments\nArgs: IP DEVICE DIW INDEX");
	}

	private static void execute(String ip, String device, String diw, int index) {
		JsonObject query = new JsonObject();
		query.add("device", device);
		query.add("diw", diw);
		query.add("index", index);

		JsonObject result = Helper.post("http://" + ip + "/firma/transactions", query);
		System.out.println(result);
	}
}
