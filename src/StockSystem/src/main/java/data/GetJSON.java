package data;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetJSON {
	public static String getJSON(String url) {

		try {
			HttpGet request = new HttpGet(url);
			request.setHeader("X-Auth-Code", "99ca0c55d4389f6e935cba03c7f1c74f");
			// 执行http get请求
			HttpResponse response = HttpClients.createDefault().execute(request);

			// 根据返回码判断返回是否成功
			String result = "";
			if (response.getStatusLine().getStatusCode() == 200) {

				result = EntityUtils.toString(response.getEntity());
			}else{
				System.out.println("fail");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
