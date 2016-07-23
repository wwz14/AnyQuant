package utils;

import java.math.BigDecimal;
/**
 * 用于将涨跌幅 显示为％的形式，％前数字保留两位小数
 * @author alice
 *
 */

public class changeRateFormate {
	
	public static String formate(BigDecimal b) {
		b = b.multiply(new BigDecimal(100));
		double data = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		//b.setScale(5,BigDecimal.ROUND_HALF_UP);
		return data + "%";		
	}
}
