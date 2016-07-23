package presentation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TestGet {

	public static String getJSON(URL url) {
		String line;
		StringBuilder builder = new StringBuilder();
		try {
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("X-Auth-Code", "99ca0c55d4389f6e935cba03c7f1c74f");
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			br.close();
			isr.close();
			is.close();

			System.out.println("JSON: " + builder.toString());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return builder.toString();
	}

}
