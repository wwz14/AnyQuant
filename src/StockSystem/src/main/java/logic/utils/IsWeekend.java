package logic.utils;
/**
 * by Summer Sakura
 */
import java.util.Calendar;
import java.util.Date;

public class IsWeekend {
    /**
     * 判断是否为周末，如果是返回true
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

}
