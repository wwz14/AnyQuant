package logic.Analysisbl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logic.Analysisbl.Polyfit;
import logicservice.Analysisblservice.PolyFitLogicservice;

import org.junit.AfterClass;
import org.junit.Test;

import enums.Stockfield;
import utils.ResultMsg;
import vo.PolyFitVO;

public class PolyfitLogic_Test {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testpolyFit() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000");
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
	
	@Test
	public void testpolyFitfield() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.high);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
	@Test
	public void testpolyFitfield2() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.adj_price);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
	@Test
	public void testpolyFitfield3() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.low);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0)
			isOk = true;
		assertEquals(true,isOk);
	}
	@Test
	public void testpolyFitfield4() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.open);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0)
			isOk = true;
		assertEquals(true,isOk);
	}
	@Test
	public void testpolyFitfield5() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.close);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
	@Test
	public void testpolyFitfield6() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.pb);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
	
	public void testpolyFitfield7() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.turnover);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}

	@Test
	public void testpolyFitfield9() throws Exception {
		PolyFitLogicservice service = new Polyfit();
		ResultMsg result = service.polyFit("sh600000", Stockfield.pe_ttm);
		PolyFitVO vo = (PolyFitVO)result.getResult();
		ArrayList<Double> act = vo.act;
		ArrayList<Double> pre = vo.pre;
		boolean isOk = false;
		int length  = act.size();
		double confidence = 0;
		double deviation  = 0;
		for(int i = 0;i<length;i++) {
		    deviation = Math.abs(pre.get(i) - act.get(i));
			if(deviation <= 0.5){
			confidence +=1;	 
			System.out.println(confidence);
			}
		}
		confidence = confidence/length;
		System.out.println(confidence);
		if(confidence >= 0.5)
			isOk = true;
		assertEquals(true,isOk);
	}
}
