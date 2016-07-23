package presentation.spider;

public class Report {
	String id ;//序号
	String title ;//标题
	String type; //报告类型
	String date;//发布日期
	String org;//机构
	String reporter;//研究员
	String link ;//链接
	
	
	
	public Report(String id, String title, String type, String date, String org, String reporter,
			String link) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.date = date;
		this.org = org;
		this.reporter = reporter;
		this.link = link;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "IndustryReportList [id=" + id + ", title=" + title + ", type=" + type + ", date=" + date + ", org="
				+ org + ", reporter=" + reporter + ", link=" + link + "]";
	}
	
	
	
}
