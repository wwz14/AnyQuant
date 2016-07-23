package presentation.graph.jfreechart;

import java.util.List;

import javax.swing.JPanel;

import enums.KLineType;
import presentation.graph.util.StatisticalIndicators;
import vo.BollVO;
import vo.KDJVO;
import vo.KLineVO;
import vo.MAVO;
import vo.MacdVO;
import vo.StockStatisticVO;
/**
 * 
* @ClassName: KLineFactory    
* @Description: 
* @author zhuding    
* @date 2016年4月13日 下午6:55:27    
*
 */
public class KLineFactory {

	/**
	 * 创建KLinePainter画图器用于画图
	 * @param datas K线数据
	 * @param maDatas MA数据
	 * @param kLineType K线图的种类（日/周/月）
	 * @return
	 */
	public static KLinePainter createKLinePainter(List<KLineVO> datas, List<MAVO> maDatas, KLineType kLineType) {
		KLinePainter kLinePainter = null;
		switch (kLineType) {
		case day:
			kLinePainter = new DailyKLinePainter(datas, maDatas);
			break;
		case week:
			kLinePainter = new WeeklyKLinePainter(datas, maDatas);
			break;
		case month:
			kLinePainter = new MonthlyKLinePainter(datas, maDatas);
			break;
		}
		return kLinePainter;
	}

	/**
	 * 获得包含各种指标统计图的panel
	 * @param kLinePainter 画图器
	 * @param data 画图所需数据
	 * @param statisticalIndicators 统计指标的种类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JPanel addStatisticalIndicators(KLinePainter kLinePainter, Object data,
			StatisticalIndicators statisticalIndicators) {
		if (kLinePainter == null) {
			return null;
		}
		JPanel resultPanel = null;
		switch (statisticalIndicators) {
		case KDJ:
			resultPanel = kLinePainter.getPanelWithKDJ((List<KDJVO>) data);
			break;
		case ATR:
			resultPanel = kLinePainter.getPanelWithATR((List<StockStatisticVO>) data);
			break;
		case BOLL:
			resultPanel = kLinePainter.getPanelWithBoll((List<BollVO>) data);
			break;
		case MACD:
			resultPanel = kLinePainter.getPanelWithMACD((List<MacdVO>) data);
			break;
		default:
			resultPanel = kLinePainter.getBasePanel();
		}
		
		return resultPanel;
	}
	
}
