package presentation.spider;

/**
 * 阶段表现
 * @author xyf
 *
 */
public class PeriodItem {

	/*
	 * {symbol:"sh600519",name:"贵州茅台",_5low:"247.3900",_5high:"262.3900",
	 * _5changes:"0.0235",_10low:"240.3700",_10high:"262.3900",_10changes:
	 * "0.0493",_20low:"235.8000",_20high:"262.3900",_20changes:"-0.0074",_60low
	 * :"199.6000",_60high:"262.3900",_60changes:"0.2384",day:"2016-05-06",flag5
	 * :"0",flag10:"0",flag20:"0",flag60:"0"},
	 * 
	 */
	String symbol;
	String name;
	String _5low;
	String _5high;
	String _5changes;
	String _10low;
	String _10high;
	String _10changes;
	String _20low;
	String _20high;
	String _20changes;

	String day;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String get_5low() {
		return _5low;
	}

	public void set_5low(String _5low) {
		this._5low = _5low;
	}

	public String get_5high() {
		return _5high;
	}

	public void set_5high(String _5high) {
		this._5high = _5high;
	}

	public String get_5changes() {
		return _5changes;
	}

	public void set_5changes(String _5changes) {
		this._5changes = _5changes;
	}

	public String get_10low() {
		return _10low;
	}

	public void set_10low(String _10low) {
		this._10low = _10low;
	}

	public String get_10high() {
		return _10high;
	}

	public void set_10high(String _10high) {
		this._10high = _10high;
	}

	public String get_10changes() {
		return _10changes;
	}

	public void set_10changes(String _10changes) {
		this._10changes = _10changes;
	}

	public String get_20low() {
		return _20low;
	}

	public void set_20low(String _20low) {
		this._20low = _20low;
	}

	public String get_20high() {
		return _20high;
	}

	public void set_20high(String _20high) {
		this._20high = _20high;
	}

	public String get_20changes() {
		return _20changes;
	}

	public void set_20changes(String _20changes) {
		this._20changes = _20changes;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
