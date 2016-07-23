package enums;

public enum KLineType {
	day("日K线图"),
	
	week("周K线图"), 
	
	month("月K线图");

	private String type;

	KLineType(String s) {
		type = s;
	}

	@Override
	public String toString() {
		return this.type;
	}
}
