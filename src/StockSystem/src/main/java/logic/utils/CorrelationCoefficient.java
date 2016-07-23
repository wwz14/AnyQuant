//相关系数
package logic.utils;

public class CorrelationCoefficient {
	/**
	 * 相关系数
	 * arg1,arg2size要一样
	 * @param arg1 
	 * @param arg2
	 * 
	 * @return
	 */
	   public static double correlationCoefficient(double[] arg1, double[] arg2) {
	        double[] sum = new double[arg1.length];
	        if(arg1.length!=arg2.length){
	        	System.out.println("size不一样，不能算相关系数");
	        }
	        for (int i = 0; i < arg1.length; i++) {
	            sum[i] = arg1[i] * arg2[i];
	        }
	        Statistics sum1=new Statistics(sum);
	        Statistics a1=new Statistics(arg1);
	        Statistics a2=new Statistics(arg2);
	        double cov = sum1.getMeanValue() - a1.getMeanValue() * a2.getMeanValue();
	        double p = cov / (a1.getSdValue() * a2.getSdValue());
	        return p;
	    }
}
