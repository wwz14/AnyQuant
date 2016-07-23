package utils;

import java.io.InputStream;
import java.net.URL;

public class NetStatus {
	private NetStatus() {
	}

	public static boolean isConnected() {
		URL url = null;
		boolean result = false;
		try {
			url = new URL("http://baidu.com");

			InputStream in = url.openStream();
			in.close();
			
			result = true;

		} catch (Exception e) {
			// not print exception
		}
		isConnected = result;
		return result;
	}

	public static boolean isConnected = false;

	public static void startNetTest() {
		NetTester netTester = new NetTester();
		netTester.start();
	}
}
