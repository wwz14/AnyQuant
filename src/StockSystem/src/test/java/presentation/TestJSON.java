package presentation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class TestJSON {
	public static void main(String[] args) {
		// readJSON();
		// createJSON();
		UseGson();
	}

	public static void readJSON() {
		JsonParser jParser = new JsonParser();

		try {
			JsonObject jObject = (JsonObject) jParser.parse(new FileReader("testFile/testRead.json"));
			System.out.println("status:" + jObject.get("status").getAsString());

			JsonObject data = jObject.get("data").getAsJsonObject();

			JsonArray array = data.get("trading_info").getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {
				System.out.println("------");
				JsonObject subObject = array.get(i).getAsJsonObject();
				System.out.println("date:" + subObject.get("date").getAsString());
				System.out.println("high:" + subObject.get("high").getAsDouble());
				System.out.println("open:" + subObject.get("open").getAsDouble());
				System.out.println("close:" + subObject.get("close").getAsDouble());

			}

			System.out.println("name:" + data.get("name").getAsString());

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 使用GSON
	public static void UseGson() {
		@SuppressWarnings("unused")
		Gson gson = new Gson();
		try {
			// 测试stocks通过
			// URL url = new URL("http://121.41.106.89:8010/api/stocks");
			// AllResultPO allResultPO = gson.fromJson(TestGet.getJSON(url),
			// AllResultPO.class);
			// System.out.println(allResultPO.getData().get(0).getLink());

			// 测试stock通过
			// URL url = new
			// URL("http://121.41.106.89:8010/api/stock/sz002644");
			// ResultPO resultPO = gson.fromJson(TestGet.getJSON(url),
			// ResultPO.class);
			// System.out.println(resultPO.getData().getTrading_info().get(0).getDate());

			// 测试benchmark/all通过
			// URL url = new URL("http://121.41.106.89:8010/api/benchmark/all");
			// AllResultPO allResultPO = gson.fromJson(TestGet.getJSON(url),
			// AllResultPO.class);
			// System.out.println(allResultPO.getData().get(0).getLink());

			// 测试benchmark/hs300通过
			// URL url = new
			// URL("http://121.41.106.89:8010/api/benchmark/hs300");
			// ResultPO resultPO = gson.fromJson(TestGet.getJSON(url),
			// ResultPO.class);
			// System.out.println(resultPO.getData().getTrading_info().get(0).getDate());

			URL url = new URL("http://121.41.106.89:8010/api/stock/sz002610");
			System.out.println(TestGet.getJSON(url));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void createJSON() {

		JsonObject jObject = new JsonObject();
		jObject.addProperty("cat", "it");

		JsonArray array = new JsonArray();

		JsonObject lan1 = new JsonObject();
		lan1.addProperty("id", 1);
		lan1.addProperty("name", "Java");
		JsonObject lan2 = new JsonObject();
		lan2.addProperty("id", 2);
		lan2.addProperty("name", "C++");
		JsonObject lan3 = new JsonObject();
		lan3.addProperty("id", 3);
		lan3.addProperty("name", "Swift");

		array.add(lan1);
		array.add(lan2);
		array.add(lan3);
		jObject.add("languages", array);
		jObject.addProperty("pop", true);

		System.out.println(jObject.toString());

	}

}
