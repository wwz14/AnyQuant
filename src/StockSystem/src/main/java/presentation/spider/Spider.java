package presentation.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import presentation.common.Toast;
import presentation.main.MainController;

/**
 *
 */
public class Spider {

	static int maxUrlCount = 10; // 允许加载的最大页

	public static String SendGet(String url) {
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			HttpURLConnection urlConn = (HttpURLConnection) realUrl.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setUseCaches(true);
			urlConn.connect();
			// 开始实际的连接
			urlConn.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),
					urlConn.getContentType().equals("text-html; charset=gb2312") ? "UTF-8" : "gb2312"));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null) {
				// 遍历抓取到的每一行并将其存储到result里面
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static ArrayList<StockPreformancePrediction> getPreformancePrediction(int page) {
		// 定义即将访问的链接
		String url = "";
		if (page == 0) {
			url = "http://vip.stock.finance.sina.com.cn/q/go.php/vPerformancePrediction/kind/eps/index.phtml";
		} else if (page < maxUrlCount) {
			url = "http://vip.stock.finance.sina.com.cn/q/go.php/vPerformancePrediction/kind/eps/index.phtml?p=" + page;
		} else {
			new Toast(MainController.frame, 1000, "请不要加载太多数据", Toast.WARING);
			return null;
		}

		ArrayList<StockPreformancePrediction> predictions = new ArrayList<StockPreformancePrediction>();
		try {
			Document doc = Jsoup.connect(url).get();

			Element tbody = doc.getElementsByTag("tbody").get(0);

			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				ArrayList<String> strings = new ArrayList<String>();
				for (Element td : tds) {
					strings.add(td.text());
				}
				Element link = tr.select("a[href]").get(4);
				String linkString = link.attr("href");
				StockPreformancePrediction stockPreformancePrediction = new StockPreformancePrediction(strings.get(0),
						strings.get(1), strings.get(2), strings.get(3), strings.get(4), strings.get(5), strings.get(6),
						strings.get(7), linkString);
				predictions.add(stockPreformancePrediction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return predictions;

	}

	public static ArrayList<StockPreformancePrediction> getPreformancePrediction(String stockCode) {
		// http://vip.stock.finance.sina.com.cn/q/go.php/vPerformancePrediction/kind/eps/index.phtml?symbol=sz300196
		// 定义即将访问的链接
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vPerformancePrediction/kind/eps/index.phtml?symbol="
				+ stockCode;
		ArrayList<StockPreformancePrediction> predictions = new ArrayList<StockPreformancePrediction>();
		try {
			Document doc = Jsoup.connect(url).get();

			Element tbody = doc.getElementsByTag("tbody").get(0);

			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				ArrayList<String> strings = new ArrayList<String>();
				for (Element td : tds) {
					strings.add(td.text());
				}
				Element link = tr.select("a[href]").get(4);
				String linkString = link.attr("href");
				StockPreformancePrediction stockPreformancePrediction = new StockPreformancePrediction(strings.get(0),
						strings.get(1), strings.get(2), strings.get(3), strings.get(4), strings.get(5), strings.get(6),
						strings.get(7), linkString);
				predictions.add(stockPreformancePrediction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return predictions;
	}

	/**
	 * @return YYQS 一月强势股
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static StrongStockJsonResult getYYQS(int page, String sort, int isAsc) {
		StrongStockJsonResult yyqs = null;
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getRiseMonthList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			yyqs = gson.fromJson(json, StrongStockJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return yyqs;
	}

	/**
	 * @return YZQS 一周强势股
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static StrongStockJsonResult getYZQS(int page, String sort, int isAsc) {
		StrongStockJsonResult yzqs = null;
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getRiseWeekList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			yzqs = gson.fromJson(json, StrongStockJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return yzqs;
	}

	/**
	 * @return DQZD 短期涨跌
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static ShortChangesJsonResult getDQZD(int page, String sort, int isAsc) {
		ShortChangesJsonResult dqzd = null;
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getShortList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			dqzd = gson.fromJson(json, ShortChangesJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dqzd;
	}

	/**
	 * @return CQZD 长期涨跌
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static LongChangesJsonResult getCQZD(int page, String sort, int isAsc) {
		LongChangesJsonResult cqzd = null;
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getLongList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			cqzd = gson.fromJson(json, LongChangesJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cqzd;
	}

	/**
	 * @return 阶段最高最低
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static PeriodJsonResult getPeriodList(int page, String sort, int isAsc) {
		PeriodJsonResult periodList = null;
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getPeriodList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			periodList = gson.fromJson(json, PeriodJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return periodList;
	}

	/**
	 * @return 流通市值排行
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static MarketValueJsonResult getMarketValueList(int page, String sort, int isAsc) {
		MarketValueJsonResult marketValueList = null;
		// Market_Center.getHQNodeDataNew?page=1&num=50&sort=nmc&asc=0&node=hs_a
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/Market_Center.getHQNodeDataNew?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=hs_a";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			marketValueList = gson.fromJson(json, MarketValueJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return marketValueList;
	}

	/**
	 * @return 连续上涨个股
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static StockRiseJsonResult getStockRiseList(int page, String sort, int isAsc) {
		StockRiseJsonResult stockRiseList = null;
		// http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/IO.XSRV2.CallbackList['t5EMB_i_mXBQgad2']/StatisticsService.getStockRiseConList?page=1&num=50&sort=day_con&asc=0&node=adr_hk
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getStockRiseConList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			stockRiseList = gson.fromJson(json, StockRiseJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockRiseList;
	}

	/**
	 * @return 连续下跌个股
	 * @param page
	 *            页数
	 * @param sort
	 *            排序的列
	 * @param isAsc
	 *            是否升序，0表示降序，1表示升序
	 * @return
	 */
	public static StockReduceJsonResult getStockReduceList(int page, String sort, int isAsc) {
		StockReduceJsonResult stockRuduceList = null;
		// http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getStockReduceConList?page=1&num=50&sort=day_con&asc=0&node=adr_hk
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/arraylist/StatisticsService.getStockReduceConList?page="
				+ page + "&num=50&sort=" + sort + "&asc=" + isAsc + "&node=adr_hk";
		try {
			Document doc = Jsoup.connect(url).get();
			String json = doc.body().text();
			json = json.replace("arraylist(", "{\"arraylist\":");
			json = json.replace(")", "}");

			Gson gson = new Gson();
			stockRuduceList = gson.fromJson(json, StockReduceJsonResult.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockRuduceList;
	}

	/**
	 * @param page
	 *            页数
	 * @return 行业报告列表
	 */
	public static ArrayList<Report> getIndustryReport(int page) {
		// 定义即将访问的链接
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vReport_List/kind/search/index.phtml?t1=3&industry=%D2%F8%D0%D0%D0%D0%D2%B5&symbol=&p=" + page;
		ArrayList<Report> industryReportlist = new ArrayList<Report>();
		try {
			Document doc = Jsoup.connect(url).get();

			Element tbody = doc.getElementsByTag("tbody").get(0);

			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 2; i < trs.size(); i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				ArrayList<String> strings = new ArrayList<String>();
				for (Element td : tds) {
					strings.add(td.text());
				}
				Element link = tr.select("a[href]").first();
				String linkString = link.attr("href");
				Report industryReport = new Report(strings.get(0), strings.get(1), strings.get(2), strings.get(3),
						strings.get(4), strings.get(5), linkString);
				industryReportlist.add(industryReport);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return industryReportlist;
	}

	/**
	 * @param page
	 *            页数
	 * @return 公司研究
	 */
	public static ArrayList<Report> getStockReport(String stockCode) {
		// 定义即将访问的链接
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vReport_List/kind/search/index.phtml?symbol="
				+ stockCode + "&t1=all&p=1";
		ArrayList<Report> industryReportlist = new ArrayList<Report>();
		try {
			Document doc = Jsoup.connect(url).get();

			Element tbody = doc.getElementsByTag("tbody").get(0);

			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 2; i < trs.size(); i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				ArrayList<String> strings = new ArrayList<String>();
				for (Element td : tds) {
					strings.add(td.text());
				}
				Element link = tr.select("a[href]").first();
				String linkString = link.attr("href");
				Report industryReport = new Report(strings.get(0), strings.get(1), strings.get(2), strings.get(3),
						strings.get(4), strings.get(5), linkString);
				industryReportlist.add(industryReport);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return industryReportlist;
	}

	/**
	 * @param page
	 *            页数
	 * @return 业绩报告
	 * @Description: 股票业绩预测的具体的研报，
	 *               例如http://vip.stock.finance.sina.com.cn/q/go.php/vReport_Show/ kind/search/rptid/3228568/index.phtml
	 */
	public static ReportShow getReportShow(String url) {
		ReportShow reportShow = null;
		try {
			Document doc = Jsoup.connect(url).get();

			Element title = doc.getElementsByTag("h1").get(0);

			Element detail = doc.getElementsByClass("creab").get(0);

			Element content = doc.getElementsByTag("p").get(0);

			// 格式化
			String contentString = content.outerHtml();

			contentString = "      " + contentString;
			contentString = contentString.replaceAll("<br>", "\n");
			contentString = contentString.replaceAll("<.*?>", "");
			contentString = contentString.replaceAll("&nbsp;", "  ");
			System.out.println(contentString);

			reportShow = new ReportShow(title.text(), detail.text(), contentString);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return reportShow;

	}

	/**
	 * @param page
	 *            页数
	 * @return 盈利能力
	 * @Description: 股票财务分析的盈利能力
	 */
	public static ArrayList<Profit> getProfit(int page, String sort, int isAsc) {
		// 定义即将访问的链接
		String url = "http://vip.stock.finance.sina.com.cn/q/go.php/vFinanceAnalyze/kind/profit/index.phtml?p=" + page
				+ "&order=" + sort;
		if (isAsc == 0) {
			url += "%7C2"; // 降序
		} else if (isAsc == 1) {
			url += "%7C1"; // 升序
		}
		ArrayList<Profit> profitlist = new ArrayList<Profit>();
		try {
			Document doc = Jsoup.connect(url).get();

			Element tbody = doc.getElementsByTag("tbody").get(0);

			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				ArrayList<String> strings = new ArrayList<String>();
				for (Element td : tds) {
					strings.add(td.text());
				}
				Profit profit = new Profit(strings.get(0), strings.get(1), strings.get(2), strings.get(3),
						strings.get(4), strings.get(5), strings.get(6), strings.get(7), strings.get(8));
				profitlist.add(profit);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return profitlist;

	}

	/**
	 * 具体股票的机构分析
	 */
	public static StockOrgAnalysis getStockOrgAnalysis(String stockCode) {
		// 定义即将访问的链接
		String url = "http://stock.finance.sina.com.cn/portfolio/api/openapi.php/ClientService.getForecastV2?code="
				+ stockCode;

		StockOrgAnalysis stockOrgAnalysis = null;
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true).get();
			String json = doc.body().text();

			json = json.replace("(", "");
			json = json.replace(")", "");

			Gson gson = new Gson();
			stockOrgAnalysis = gson.fromJson(json, StockOrgAnalysis.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockOrgAnalysis;

	}

	

}
