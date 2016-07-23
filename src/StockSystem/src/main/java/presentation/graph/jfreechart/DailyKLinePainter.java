package presentation.graph.jfreechart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;

import utils.DateTool;
import vo.KLineVO;
import vo.MAVO;

/**  
* @ClassName: DailyKLinePainter    
* @Description: 画日K线图
* @author zhuding    
*        
*/
public class DailyKLinePainter extends KLinePainter{

	public DailyKLinePainter(List<KLineVO> datas,List<MAVO> maDatas) {
		super(datas, maDatas);
	}

	@Override
	protected boolean setKLineDetail() {
		List<Date> dates = new ArrayList<>();
		for (KLineVO kLineVO : klineDatas) {
			dates.add(kLineVO.getDate());
		}
		
		Date start = DateTool.getMinDate(dates);
		Date end = DateTool.beforeDate(DateTool.getMaxDate(dates), 1);
		x1Axis.setRange(start,end);// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
		int t = (int) (DateTool.betweenDays(start, end) / 5);
		t = t < 1 ? 1 : t;
		x1Axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, t));// 设置时间刻度的间隔
		return true;
	}

	@Override
	protected RegularTimePeriod getRegularTimePeriod(Date date) {
		return new Day(date);
	}


}
