package iot.challenge.mira.backend.examples;

import com.eclipsesource.json.JsonObject;

public class Publicly {

	private static final int IP = 0;
	private static final int ID = 1;

	// ID of valid transaction
	// SVZDF9IEQCIZBBLNRX9PZKBOUQZGCPJBQINOVMNLSLWNW9FGGLGCATURHUNDLDADMUEGBZNLQJBCA9999
	public static void main(String[] args) {
		if (args.length != 2) {
			help();
		} else {
			execute(args[IP], args[ID]);
		}
	}

	private static void help() {
		System.out.println("Invalid arguments\nArgs: IP TRANSACTION-ID");
	}

	private static void execute(String ip, String id) {
		JsonObject query = new JsonObject();
		query.add("transaction", id);
		query.add("mode", "publicly");

		JsonObject result = Helper.post("http://" + ip + "/firma/validate", query);
		System.out.println(result);
	}
}
