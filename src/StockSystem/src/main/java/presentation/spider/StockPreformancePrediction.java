package presentation.spider;

public class StockPreformancePrediction {
	String stockCode;
	String stockName;
	String EPS15;
	String EPS16;
	String EPS17;
	String EPS18;
	String reportDate;
	String org;
	String reportShowURL;
	
	

	public StockPreformancePrediction(String stockCode, String stockName, String ePS15, String ePS16, String ePS17,
			String ePS18, String reportDate, String org, String reportShowURL) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		EPS15 = ePS15;
		EPS16 = ePS16;
		EPS17 = ePS17;
		EPS18 = ePS18;
		this.reportDate = reportDate;
		this.org = org;
		this.reportShowURL = reportShowURL;
	}

	// public StockPreformancePrediction(String message) {
	// stockCode = "";
	// stockName = "";
	// EPS15 = "";
	// EPS16 = "";
	// EPS17 = "";
	// EPS18 = "";
	// reportDate = "";
	//
	// Pattern pattern;
	// Matcher matcher;
	// pattern = Pattern.compile("\"http.+?target.+?>(.+?)</a>");
	// matcher = pattern.matcher(message);
	// if (matcher.find()) {
	// stockCode = matcher.group(1);
	// }
	//
	// pattern = Pattern.compile("<td.+?target.+?>(.+?)</a>");
	// matcher = pattern.matcher(message);
	// if (matcher.find()) {
	// stockName = matcher.group(1);
	// }
	//
	// pattern = Pattern.compile("<td.+?target.+?</a></td>(.+?)<td
	// style.+?</td>");
	// matcher = pattern.matcher(message);
	// ArrayList<String> strings = new ArrayList<String>();
	// if (matcher.find()) {
	// String EPSs = matcher.group(1);
	// pattern = Pattern.compile("<td>(.+?)</td>");
	// matcher = pattern.matcher(EPSs);
	// boolean isFind = matcher.find();
	// while (isFind) {
	// strings.add(matcher.group(1));
	// isFind = matcher.find();
	// }
	// }
	//
	// if (strings.size() == 4) {
	// EPS15 = strings.get(0);
	// EPS16 = strings.get(1);
	// EPS17 = strings.get(2);
	// EPS18 = strings.get(3);
	// }
	//
	// pattern = Pattern.compile("<td
	// style.+?>([\\d]{4}-[\\d]{2}-[\\d]{2})</td>");
	// matcher = pattern.matcher(message);
	// if (matcher.find()) {
	// reportDate = matcher.group(1);
	// }
	//
	// pattern = Pattern.compile("<td><a href =\"(.+?)\"
	// target=\"_blank\">摘要</a>");
	// matcher = pattern.matcher(message);
	// if (matcher.find()) {
	// reportShowURL =matcher.group(1);
	// }
	//
	// }
	//
	// public String writeString() {
	// // 拼接写入本地的字符串
	// String result = "";
	// result += "股票代号：" + stockCode + "\r\n";
	// result += "股票名称：" + stockName + "\r\n";
	// result += "15EPS：" + EPS15 + "\r\n";
	// result += "16EPS：" + EPS16 + "\r\n";
	// result += "17EPS：" + EPS17 + "\r\n";
	// result += "18EPS：" + EPS18 + "\r\n";
	// result += "报告日期：" + reportDate + "\r\n";
	// result += "研报正文链接：" + reportShowURL + "\r\n";
	//
	// // 将其中的html标签进行筛选
	// result = HTMLFilter.filter(result);
	// return result;
	// }

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getEPS15() {
		return EPS15;
	}

	public void setEPS15(String ePS15) {
		EPS15 = ePS15;
	}

	public String getEPS16() {
		return EPS16;
	}

	public void setEPS16(String ePS16) {
		EPS16 = ePS16;
	}

	public String getEPS17() {
		return EPS17;
	}

	public void setEPS17(String ePS17) {
		EPS17 = ePS17;
	}

	public String getEPS18() {
		return EPS18;
	}

	public void setEPS18(String ePS18) {
		EPS18 = ePS18;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportShowURL() {
		return reportShowURL;
	}

	public void setReportShowURL(String reportShowURL) {
		this.reportShowURL = reportShowURL;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

}
