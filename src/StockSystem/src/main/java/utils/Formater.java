package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 格式化
 * @author xyf
 *
 */
public class Formater {
	/**
	 * @param string 表示数字的String 0.3134
	 * @param format 格式化方式，例如"0.00"
	 * @return 格式化之后的String 例如0.31
	 */
	public static String formate(String string,String format){
		DecimalFormat df=new DecimalFormat(format); 
		Double d =Double.parseDouble(string);
		return df.format(d);
	}
	
	public static Double formate(double d,String format){
		DecimalFormat df = new DecimalFormat(format); //eg :"#0.00"
		return Double.parseDouble(df.format(d));
	}
	
	public static String formate(BigDecimal bigDecimal,String format){
		DecimalFormat df=new DecimalFormat(format); 
		Double d =bigDecimal.doubleValue();
		return df.format(d);
	}
	
	/**
	 * @param string 表示数字的String 0.3134
	 *   默认保留两位小数
	 * @return 格式化之后的String 例如0.31
	 */
	public static String formate(String string){
		DecimalFormat df=new DecimalFormat("0.00"); 
		Double d =Double.parseDouble(string);
		return df.format(d);
	}
	
	public static String formate(BigDecimal bigDecimal){
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		Double d =bigDecimal.doubleValue();
		return df.format(d);
	}
	
	
	/**
	 * @param string 表示数字的String 0.3134
	 * @param format 格式化方式，例如"0.00"
	 * @return 带百分号的String 31.34%
	 */
	public static String formatePercent(String string,String format){
		DecimalFormat df=new DecimalFormat(format); 
		Double d =Double.parseDouble(string);
		d=d*100;
		return df.format(d)+"%";
	}
	
	/**
	 * @param string 表示数字的String 0.3134
	 * @return 带百分号的String 31.34% 默认保留两位小数
	 */
	public static String formatePercent(String string){
		DecimalFormat df=new DecimalFormat("0.00"); 
		Double d =Double.parseDouble(string);
		d=d*100;
		return df.format(d)+"%";
	} 
	
	public static String formatePercent(BigDecimal bigDecimal){
		DecimalFormat df=new DecimalFormat("0.00"); 
		Double d =bigDecimal.doubleValue();
		d=d*100;
		return df.format(d)+"%";
	} 
	
	
	public static void main(String[] args) {
		System.out.println(Formater.formatePercent("0.3134", "0.00"));
	}
}
