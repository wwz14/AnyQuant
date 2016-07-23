package presentation.graph.jfreechart;

/**
 * 
* @ClassName: GraphData    
* @Description: 传给DataPanel的数据包
* @author zhuding    
* @date 2016年4月13日 下午7:38:03    
*
 */
public class GraphData {
	private String dateString = "0000-00-00";
	/**
	 * 开盘价
	 */
	private double Open;
	/**
	 * 收盘价
	 */
	private double Close;
	/**
	 * 最高价
	 */
	private double High;
	/**
	 * 最低价
	 */
	private double Low;
	/**
	 * 成交量
	 */
	private double volumn;
	/**
	 * 成交额
	 */
	private double dealPrice;
	/**
	 * MA5
	 */
	private double MA5;
	/**
	 * MA20
	 */
	private double MA20;
	/**
	 * MA30
	 */
	private double MA30;
	/**
	 * MA60
	 */
	private double MA60;
	/**
	 * 成交量的MA5
	 */
	private double MAVOL5;
	/**
	 * 成交量的MA10
	 */
	private double MAVOL10;
	/**
	 * ATR数据
	 */
	private double ATR;
	
	private double k;
	
	private double d;
	
	private double j;
	
	private double dif;
	/**
	 * 离差平均值
	 */
	private double dea;
	/**
	 * 
	 */
	private double macd;
	/**
	 * 白线
	 * 中轨线=N日的移动平均线
	 * */
	private double mb;
	/**
	 * 黄线
	 * 上轨线=中轨线+两倍的标准差
	 * */
	private double up;
	/**
	 * 紫线
	 * 下轨线下轨线=中轨线－两倍的标准差
	 * */
	private double dn;
	public double getOpen() {
		return Open;
	}
	public void setOpen(double open) {
		this.Open = open;
	}
	public double getClose() {
		return Close;
	}
	public void setClose(double close) {
		this.Close = close;
	}
	public double getHigh() {
		return High;
	}
	public void setHigh(double high) {
		this.High = high;
	}
	public double getLow() {
		return Low;
	}
	public void setLow(double low) {
		this.Low = low;
	}
	public double getVolumn() {
		return volumn;
	}
	public void setVolumn(double volumn) {
		this.volumn = volumn;
	}
	public double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public double getMA5() {
		return MA5;
	}
	public void setMA5(double mA5) {
		MA5 = mA5;
	}
	public double getMA20() {
		return MA20;
	}
	public void setMA20(double mA20) {
		MA20 = mA20;
	}
	public double getMA30() {
		return MA30;
	}
	public void setMA30(double mA30) {
		MA30 = mA30;
	}
	public double getMA60() {
		return MA60;
	}
	public void setMA60(double mA60) {
		MA60 = mA60;
	}
	public double getMAVOL5() {
		return MAVOL5;
	}
	public void setMAVOL5(double mAVOL5) {
		MAVOL5 = mAVOL5;
	}
	public double getMAVOL10() {
		return MAVOL10;
	}
	public void setMAVOL10(double mAVOL10) {
		MAVOL10 = mAVOL10;
	}
	public double getATR() {
		return ATR;
	}
	public void setATR(double aTR) {
		ATR = aTR;
	}
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	public double getJ() {
		return j;
	}
	public void setJ(double j) {
		this.j = j;
	}
	public double getDif() {
		return dif;
	}
	public void setDif(double dif) {
		this.dif = dif;
	}
	public double getDea() {
		return dea;
	}
	public void setDea(double dea) {
		this.dea = dea;
	}
	public double getMacd() {
		return macd;
	}
	public void setMacd(double macd) {
		this.macd = macd;
	}
	public double getMb() {
		return mb;
	}
	public void setMb(double mb) {
		this.mb = mb;
	}
	public double getUp() {
		return up;
	}
	public void setUp(double up) {
		this.up = up;
	}
	public double getDn() {
		return dn;
	}
	public void setDn(double dn) {
		this.dn = dn;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
	
}
