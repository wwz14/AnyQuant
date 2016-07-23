package presentation.spider;

import java.io.Serializable;

public class LongChangesItem implements Serializable {
	
	/*
	 * 
	 *{symbol:"sz002793",name:"东音股份",
		_10changes:"1.5956",_10turnover:"0.7105",_10aov:"1.3592",
		_20changes:"0.0000",_20turnover:"0.7119",_20aov:"3.1483",
		_30changes:"0.0000",_30turnover:"0.7119",_30aov:"3.1483",
		_60changes:"0.0000",_60turnover:"0.7119",_60aov:"3.1483",
		day:"2016-05-05"}
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7902493946874110558L;
	String symbol;
	String name;
	String _10changes;
	String _10turnover;
	String _10aov;
	String _30changes;
	String _30turnover;
	String _30aov;
	
	String _60changes;
	String _60turnover;
	String _60aov;
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
	public String get_10changes() {
		return _10changes;
	}
	public void set_10changes(String _10changes) {
		this._10changes = _10changes;
	}
	public String get_10turnover() {
		return _10turnover;
	}
	public void set_10turnover(String _10turnover) {
		this._10turnover = _10turnover;
	}
	public String get_10aov() {
		return _10aov;
	}
	public void set_10aov(String _10aov) {
		this._10aov = _10aov;
	}
	public String get_30changes() {
		return _30changes;
	}
	public void set_30changes(String _30changes) {
		this._30changes = _30changes;
	}
	public String get_30turnover() {
		return _30turnover;
	}
	public void set_30turnover(String _30turnover) {
		this._30turnover = _30turnover;
	}
	public String get_30aov() {
		return _30aov;
	}
	public void set_30aov(String _30aov) {
		this._30aov = _30aov;
	}
	public String get_60changes() {
		return _60changes;
	}
	public void set_60changes(String _60changes) {
		this._60changes = _60changes;
	}
	public String get_60turnover() {
		return _60turnover;
	}
	public void set_60turnover(String _60turnover) {
		this._60turnover = _60turnover;
	}
	public String get_60aov() {
		return _60aov;
	}
	public void set_60aov(String _60aov) {
		this._60aov = _60aov;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	
	
}
