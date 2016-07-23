package presentation;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.KLineType;
import presentation.graph.jfreechart.DailyKLinePainter;
import presentation.graph.jfreechart.KLinePainter;
import presentation.panel.chartPanel.BenchmarkKChartPanel;
import presentation.spider.Spider;
import vo.KLineVO;
import vo.MAVO;

public class SpiderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Spider.getPeriodList(1, "_5high", 0);
		Spider.getDQZD(1, "_3changes", 0);
		Spider.getCQZD(1, "_10changes", 0);
		Spider.getYZQS(1, "changes", 0);
		Spider.getYYQS(1, "changes", 0);
		Spider.getMarketValueList(1, "nmc", 0);
		Spider.getStockReduceList(1, "day_con", 0);
		Spider.getIndustryReport(1);
		Spider.SendGet("http://vip.stock.finance.sina.com.cn/q/go.php/vPerformancePrediction/kind/eps/index.phtml");

		BigDecimal kkk = new BigDecimal(0.1);
		KLineVO k = new KLineVO(kkk, kkk, kkk, kkk, kkk, kkk, new Date());
		MAVO ma2 = new MAVO(kkk, kkk, kkk, kkk, kkk, kkk, new Date());
		List<KLineVO> datas = new ArrayList<KLineVO>();
		List<MAVO> maDatas = new ArrayList<MAVO>();
		// KLinePainter a=new DailyKLinePainter(datas,maDatas);
		// a.getBasePanel();a.getChart();a.getDateAxis();a.getNumberAxis();

	}

}
