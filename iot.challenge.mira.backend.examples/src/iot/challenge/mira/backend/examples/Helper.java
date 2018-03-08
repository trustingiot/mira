package iot.challenge.mira.backend.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.eclipsesource.json.JsonObject;

public class Helper {

	public static JsonObject read(HttpResponse response) throws UnsupportedOperationException, IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer result = new StringBuffer();
		String buffer = "";
		while ((buffer = bufferedReader.readLine()) != null) {
			result.append(buffer);
		}

		return JsonObject.readFrom(result.toString());
	}

	public static JsonObject post(String uri, JsonObject query) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(uri);
			StringEntity params = new StringEntity(query.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			return read(response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new JsonObject();
	}
}
