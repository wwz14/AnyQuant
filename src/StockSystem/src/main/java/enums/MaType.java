package enums;

public enum MaType {
	/**
	 * ma5,今天，昨天，。。。。的收盘价之和除以5
	 * */
	MA5(5),
 MA20(20),
	 MA30(30),
	 MA60(60),
	 /**
	  * mavol5，今天，昨天，的成交价之和除以5
	  * */
 MAVOL5(5),
MAVOL10(10);

	private int type;

	MaType(int s) {
		type = s;
	}

	public int getVal() {
		return this.type;
	}
}
