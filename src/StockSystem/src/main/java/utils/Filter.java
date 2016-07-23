package utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Description: (用于封装筛选选项)
 * 
 * @version V1.0
 */
public class Filter {
	ArrayList<FilterItem> filterItems;
	/* 假设
    open("开盘价"), 
    high("最高价"),   
    low("最低价"),  
    close("收盘价")    
    adj_price("后复权价"),   
    volume("成交量"),   
   turnover("换手率"),  
   pe("市盈率"), 
   pb("市净率"),
   date("时间");
   */
	

	Date startDate;

	Date endDate;

	
	
	public Filter(ArrayList<FilterItem> filterItems, 
			Date startDate, Date endDate) {
		super();
		this.filterItems = filterItems;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ArrayList<FilterItem> getFilterItems() {
		return filterItems;
	}

	public void setFilterItems(ArrayList<FilterItem> filterItems) {
		this.filterItems = filterItems;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
