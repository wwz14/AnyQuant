package presentation.spider;

import java.io.Serializable;

/**
 * 短期涨跌幅统计 item
 * 
 * @author xyf
 *
 */
public class ShortChangesItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6026442492547216654L;
	String symbol;
	String name;
	String _2changes;
	String _2turnover;
	String _2aov;
	String _3changes;
	String _3turnover;
	String _3aov;

	String _5changes;
	String _5turnover;
	String _5aov;
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

	public String get_2changes() {
		return _2changes;
	}

	public void set_2changes(String _2changes) {
		this._2changes = _2changes;
	}

	public String get_2turnover() {
		return _2turnover;
	}

	public void set_2turnover(String _2turnover) {
		this._2turnover = _2turnover;
	}

	public String get_2aov() {
		return _2aov;
	}

	public void set_2aov(String _2aov) {
		this._2aov = _2aov;
	}

	public String get_3changes() {
		return _3changes;
	}

	public void set_3changes(String _3changes) {
		this._3changes = _3changes;
	}

	public String get_3turnover() {
		return _3turnover;
	}

	public void set_3turnover(String _3turnover) {
		this._3turnover = _3turnover;
	}

	public String get_3aov() {
		return _3aov;
	}

	public void set_3aov(String _3aov) {
		this._3aov = _3aov;
	}

	public String get_5changes() {
		return _5changes;
	}

	public void set_5changes(String _5changes) {
		this._5changes = _5changes;
	}

	public String get_5turnover() {
		return _5turnover;
	}

	public void set_5turnover(String _5turnover) {
		this._5turnover = _5turnover;
	}

	public String get_5aov() {
		return _5aov;
	}

	public void set_5aov(String _5aov) {
		this._5aov = _5aov;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
