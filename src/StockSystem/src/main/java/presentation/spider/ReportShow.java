package presentation.spider;

/**
 * @Description: 股票业绩预测的具体的研报，
 *               例如http://vip.stock.finance.sina.com.cn/q/go.php/vReport_Show/
 *               kind/search/rptid/3228568/index.phtml
 *
 * 
 */
public class ReportShow {
	String title;
	String detail;
	String content;

	public ReportShow(String title, String detail, String content) {
		this.title = title;
		this.detail = detail;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
