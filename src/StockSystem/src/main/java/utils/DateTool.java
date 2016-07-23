package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.Week;

/**
 * 与时间有关的操作与判断
 * 
 * 确定最新数据的时间
 * 1.周末无数据
 * 2.在交易日的交易时间无法获取当日数据
 * @author zhuding
 * 
 */
public class DateTool {
	
	//日期格式
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private Date beginDate;
	
	private Date copyOfBeginDate;
	
	public DateTool(Date beginDate) {
		super();
		this.beginDate = beginDate;
		this.copyOfBeginDate = beginDate;
	}
	
	/**
	 * 获取下一个交易日的日期（并将beginDate移至该日期）
	 * @return
	 */
	public Date nextMarketDay() {
		if (beginDate == null) {
			return null;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);

		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			date.add(Calendar.DATE, 2);
		} else if (date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			date.add(Calendar.DATE, 3);
		} else {
			date.add(Calendar.DATE, 1);
		}

		beginDate = date.getTime();
		return beginDate;
	}

	/**
	 * 将beginDate复原至构造时传入的时间
	 */
	public void reset() {
		this.beginDate = this.copyOfBeginDate;
	}
	
	/**
	 * 获得最新交易日的日期
	 * @return
	 */
	public static Calendar getNewestTime(){
		Calendar date = Calendar.getInstance(); 
		date.add(Calendar.DATE, -1);
		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			date.add(Calendar.DATE, -2);
		} else if (date.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			date.add(Calendar.DATE, -3);
		} else {
			date.add(Calendar.DATE, -1);
		}
		return date;
	}
	
	
	public static String getNewestDay() {
		Calendar date = Calendar.getInstance(); 
		//date.add(Calendar.DATE, -1);
		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			date.add(Calendar.DATE, -2);
		} else if (date.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			date.add(Calendar.DATE, -3);
		} else {
			date.add(Calendar.DATE, -1);
		}
		return getStringByDate(date.getTime());
	}
	
	/**
	 * 判断这一天是否是周末
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		   Calendar c = Calendar.getInstance();
		   c.setTime(date);
		   if((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
			   return true;
		   else 
			   return false;		
	}
	
	/**
	 * 按指定格式获得日期的字符串
	 * @param date
	 * @return
	 */
	public static String getStringByDate(Date date) {
		return DATE_FORMAT.format(date);
	}


	/**
	 * 通过字符串获取date
	 * @param string
	 * @return
	 */
	public static Date getDateByString(String string) {
		Date date = null;
		try {
			date = DATE_FORMAT.parse(string);
		} catch (ParseException e) {
			System.out.println("date 格式不对");
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 获得某天前后的天数
	 * @param d，已知日期
	 * @param days，days<0，返回，d前｜days｜天，days>0，d后days天
	 * @return
	 */
	public static Date beforeDate(Date d,int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, days);
		
		return c.getTime();
	}
	public static String beforeDays(String d,int days) {
		Calendar c = Calendar.getInstance();
		Date dd=getDateByString(d);
		c.setTime(dd);
		c.add(Calendar.DATE, -days);
		
		return getStringByDate(c.getTime());
	}
	public static void main(String[] args) {
//		Date date = new Date();
//		System.out.println(DATE_FORMAT.format(getTheFirstDay(date)));
//		System.out.println(DATE_FORMAT.format(getTheFirstdayOfWeek(date)));
//		System.out.println(DATE_FORMAT.format(getTheLastDay(date)));
//		System.out.println(DATE_FORMAT.format(getTheLastDayOfWeek(date)));
//		List<KLineVO> kLineVOs = GraphTester.getKLineVOs(KLineType.day);
//		List<Date> dates = new ArrayList<>();
//		for (KLineVO kLineVO : kLineVOs) {
//			dates.add(kLineVO.getDate());
//		}
//		System.out.println(isSamePeriod(dates, PeriodType.DAY));
//		System.out.println(isSamePeriod(dates, PeriodType.WEEK));
//		System.out.println(isSamePeriod(dates, PeriodType.MONTH));
//		System.out.println(today());
		System.out.println(currentDay());
	}
	
	/**
	 * 获得每个月的第一天
	 * @param d
	 * @return
	 */
	public static Date getTheFirstDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH,1);
		return c.getTime();
	}
	/**
	 * 获得每个月的最一天
	 * @param d
	 * @return
	 */
	public static Date getTheLastDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	/**
	 * 获得次日期所属周的第一天
	 * @param d
	 * @return
	 */
	public static Date getTheFirstdayOfWeek(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int today = c.get(Calendar.DAY_OF_WEEK);
		int monday = Calendar.MONDAY;
		//c.set(Calendar.DATE,last);
		c.add(Calendar.DATE, monday-today);
		return c.getTime();	
	}
	
	/**
	    * 获得次日期所属周的最后一天,周日
	    * @param d
	    * @return
	    */
		public static Date getTheSunDayOfWeek(Date d) {
			Date friday=getTheLastDayOfWeek(d);
			
			return beforeDate(friday,2);
		}
	
	
   /**
    * 获得次日期所属周的最后一天,周五
    * @param d
    * @return
    */
	public static Date getTheLastDayOfWeek(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
	    int num = c.get(Calendar.DAY_OF_WEEK);
	    int friday = Calendar.FRIDAY;
		c.add(Calendar.DATE, friday-num);
		return c.getTime();
	}
	
	/**
	 * 获得指定时间序列中最靠后的日期
	 * @param dates
	 * @return
	 */
	public static Date getMaxDate(List<Date> dates) {
		if(dates.size() == 0)
			return null;
		Date result = dates.get(0);
		for (Date date : dates) {
			if(date.after(result)){
				result = date;
			}
		}
		return result;
	}
	
	/**
	 * 获得指定时间序列中最靠前的日期
	 * @param dates
	 * @return
	 */
	public static Date getMinDate(List<Date> dates) {
		if(dates.size() == 0)
			return null;
		Date result = dates.get(0);
		for (Date date : dates) {
			if(date.before(result)){
				result = date;
			}
		}
		return result;
	}
    
    /**
     * 两天期间隔了多少天
     * @param start
     * @param end
     * @return
     */
    public static long betweenDays(Date start,Date end) {
        long betweenDay = (long)((end.getTime()-start.getTime()) / (1000 * 60 * 60 *24) + 0.5);
        return betweenDay + 1;
    }
    
    /**
     * 判断时间序列里有无属于同一时期的值
     */
    public static boolean isSamePeriod(List<Date> dates, PeriodType type) {
    	boolean result = false;
    	for (Date date1 : dates) {
			for (Date date2 : dates) {
				if(date1.equals(date2))
					continue;
				switch (type) {
				case DAY:
					Day day1 = new Day(date1);
					Day day2 = new Day(date2);
					result = day1.equals(day2);
					break;
				case MONTH:
					Month month1 = new Month(date1);
					Month month2 = new Month(date2);
					result = month1.equals(month2);
					break;
				case WEEK:
					Week week1 = new Week(date1);
					Week week2 = new Week(date2);
					result = week1.equals(week2);
					break;
				}
				if(result) break;
			}
			if(result) break;
		}
    	return result;
	}
    
    public static String  today() {
    	Calendar c = Calendar.getInstance();
    	return getStringByDate(c.getTime());
    }
   
    public static Date  currentDay() {
    	Calendar c = Calendar.getInstance();
    	return c.getTime();
    }
}
