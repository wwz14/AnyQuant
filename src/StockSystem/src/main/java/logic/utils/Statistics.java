
package logic.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Statistics {
	 private double[] inputData; // input data  
	    private double medianValue; // median value  
	    private double meanValue; // mean of input data array  
	    private double maxValue; // max value of array  
	    private double minValue; // min value of array  
	    private double sdValue; // standard deviation of array  
	    private double sumValue; // sum of array  
	    public Statistics(double[] input) { 
	    	
	        this.inputData = input;  
	        Arrays.sort(inputData);  
	        double N = inputData.length;  
	        for(int i=0; i<inputData.length; i++)   
	        {  
	            if(i == 0) {  
	                maxValue = inputData[i];  
	                minValue = inputData[i];  
	            }  
	              
	            if(maxValue < inputData[i]) {  
	                maxValue = inputData[i];  
	            }  
	              
	            if(minValue > inputData[i]) {  
	                minValue = inputData[i];  
	            }  
	              
	            sumValue += inputData[i];  
	        }  
	        meanValue = sumValue/N;  
	          
	        // if total number is odd  
	        // calculate standard deviation and median value  
	        // http://en.wikipedia.org/wiki/Standard_deviation  
	        if(isOdd(inputData.length))   
	        {  
	            medianValue = inputData[inputData.length/2];  
	              
	        }   
	        else   
	        {  
	            double temp = inputData[inputData.length/2] + inputData[(inputData.length/2 -1)];  
	            medianValue = temp/2.0d;  
	        }  
	          
	        double powSum = 0.0d;  
	        for(int k=0; k<inputData.length; k++) {  
	            powSum += Math.pow((inputData[k] - meanValue), 2);  
	        }  
	  
	        // This correction (the use of N − 1 instead of N) is known as Bessel's correction  
	        sdValue = Math.sqrt(powSum/(N-(double)1.0d));  
	          
	          
	    }  	      
	    public Statistics(ArrayList<Double> input) { 
	    	double[] inputarray = new double[input.size()];
	    	for(int i=0;i<input.size();i++){
	    		inputarray[i] = input.get(i);
	    	}
	        this.inputData = inputarray;  
	        Arrays.sort(inputData);  
	        double N = inputData.length;  
	        for(int i=0; i<inputData.length; i++)   
	        {  
	            if(i == 0) {  
	                maxValue = inputData[i];  
	                minValue = inputData[i];  
	            }  
	              
	            if(maxValue < inputData[i]) {  
	                maxValue = inputData[i];  
	            }  
	              
	            if(minValue > inputData[i]) {  
	                minValue = inputData[i];  
	            }  
	              
	            sumValue += inputData[i];  
	        }  
	        meanValue = sumValue/N;  
	          
	        // if total number is odd  
	        // calculate standard deviation and median value  
	        // http://en.wikipedia.org/wiki/Standard_deviation  
	        if(isOdd(inputData.length))   
	        {  
	            medianValue = inputData[inputData.length/2];  
	              
	        }   
	        else   
	        {  
	            double temp = inputData[inputData.length/2] + inputData[(inputData.length/2 -1)];  
	            medianValue = temp/2.0d;  
	        }  
	          
	        double powSum = 0.0d;  
	        for(int k=0; k<inputData.length; k++) {  
	            powSum += Math.pow((inputData[k] - meanValue), 2);  
	        }  
	  
	        // This correction (the use of N − 1 instead of N) is known as Bessel's correction  
	        sdValue = Math.sqrt(powSum/(N-(double)1.0d));  
	          
	          
	    }  
	      
	    private boolean isOdd(int n) {  
	        if((n & 0x1) == 1)  
	            return true;  
	        else   
	            return false;  
	    }  
	  
	    public double getMedianValue() {  
	        return medianValue;  
	    }  
	  
	    public double getMeanValue() {  
	        return meanValue;  
	    }  
	  
	    public double getMaxValue() {  
	        return maxValue;  
	    }  
	  
	    public double getMinValue() {  
	        return minValue;  
	    }  
	  
	    public double getSdValue() {  
	        return sdValue;  
	    }  
	  
	    public double getSumValue() {  
	        return sumValue;  
	    }  
	      
	 
}
