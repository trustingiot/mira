package iot.challenge.mira.backend.examples;

import com.eclipsesource.json.JsonObject;

public class Anonymously {

	private static final int IP = 0;
	private static final int ID = 1;
	private static final int KEY = 2;

	// ID of transaction with key
	// FILPVVALEAQWFPSAPBRBAQHEKLLEPS9YOQZQAALVFGAVWNMRIKLWYGABDIRCKFIH9NVHMWKYFDNC99999
	// jmrMnh+kTdzJ5wm6QwXBUK8Wn7O1HzE=
	public static void main(String[] args) {
		if (args.length != 3) {
			help();
		} else {
			execute(args[IP], args[ID], args[KEY]);
		}
	}

	private static void help() {
		System.out.println("Invalid arguments\nArgs: IP TRANSACTION-ID TRANSACTION-KEY");
	}

	private static void execute(String ip, String id, String key) {
		JsonObject query = new JsonObject();
		query.add("transaction", id);
		query.add("key", key);
		query.add("mode", "anonymously");

		JsonObject result = Helper.post("http://" + ip + "/firma/validate", query);
		System.out.println(result);
	}
}
