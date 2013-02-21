package com.codemonkey.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Calc {

	private static final int DEF_SCALE = 2; 
	private Calc() {} 
	
	public static double abs(Double v){
		double val = 0;
		if(v != null){
			val = Math.abs(v);
		}
		return val;
	}

	public static double add(Double...values) {
		BigDecimal result = new BigDecimal("0");
		for (Double v : values) {
			if(v == null) v = 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			result = result.add(vb);
		}
		return result.doubleValue(); 
	}

	public static double sub(Double...values) {
		BigDecimal result = null;
		for (Double v : values) {
			if(v == null) v = 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			if(result == null) {
				result = vb;
			} else {
				result = result.subtract(vb);
			}
		}
		return result == null ? 0.0 : result.doubleValue();
	} 

	public static double mul(Double...values) {
		BigDecimal result = null;
		for (Double v : values) {
			if(v == null) return 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			if(result == null) {
				result = vb;
			} else {
				result = result.multiply(vb);
			}
		}
		return result == null ? 0.0 : round(result.doubleValue());
	}
	
	public static double mul(int scale , Double...values) {
		BigDecimal result = null;
		for (Double v : values) {
			if(v == null) return 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			if(result == null) {
				result = vb;
			} else {
				result = result.multiply(vb);
			}
		}
		return result == null ? 0.0 : round(result.doubleValue() , scale);
	}
	
	public static double mulNoRound(Double...values) {
		BigDecimal result = null;
		for (Double v : values) {
			if(v == null) return 0.0;
			BigDecimal vb = new BigDecimal(Double.toString(v));
			if(result == null) {
				result = vb;
			} else {
				result = result.multiply(vb);
			}
		}
		return result == null ? 0.0 : result.doubleValue();
	}

	public static double div(Double v1, Double v2) { 
		return div(v1, v2, DEF_SCALE); 
	} 

	public static double div(Double v1, Double v2, int scale) { 
		if(v1 == null) v1 = 0.0;
		if(v2 == null || v2 == 0) throw new IllegalArgumentException("The v2 must not be zero");
		if(scale < 0) throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
		
		BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	} 

	public static double round(Double v) { 
		if(v == null) v = 0.0;
		return round(v, DEF_SCALE);
	}
	
	public static double round(Double v, int scale) { 
		if(v == null) v = 0.0;
		if(scale < 0) throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
		
		BigDecimal b = new BigDecimal(Double.toString(v)); 
		BigDecimal one = new BigDecimal("1"); 
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	public static double floor(Double v) { 
		return floor(v, 0); 
	}
	public static double floor(Double v, int scale) { 
		if(v == null) v = 0.0;
		if(scale < 0) throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
		
		BigDecimal b = new BigDecimal(Double.toString(v)); 
		BigDecimal one = new BigDecimal("1"); 
		return b.divide(one,scale,BigDecimal.ROUND_FLOOR).doubleValue(); 
	}
	
	public static List<Double> split(double total , List<Double> list){
		
		if(!validateRateArray(list)){
			return null;
		}
		
		List<Double> result = new ArrayList<Double>();
		if(list == null || list.isEmpty()){
			result.add(total);
			return result;
		}
		for(int i = 0 ; i < list.size() ; i++){
			result.add(split(total , list , i));
		}
		return result;
	}
	
	public static Double split(double totalAmount , List<Double> list , int i){
		double amount = 0;
		boolean isLast = true;
		Double rate = null;
		
		if(!validateRateArray(list)){
			return null;
		}
		
		if(list != null && !list.isEmpty()){
			rate = list.get(i);
			isLast = list.size()-1 == i;
		}
		
		if(rate == null){
			amount = totalAmount;
		}else if(isLast){
			double accumulateDocTotal = 0;
			for(int j = 0 ; j < list.size()-1 ; j++){
				accumulateDocTotal += Calc.mul(totalAmount , list.get(j) / 100);
			}
			amount = Calc.sub(totalAmount , accumulateDocTotal);
			
		}else{
			amount = Calc.mul(totalAmount , rate / 100);
		}
		return amount;
	}
	
	private static boolean validateRateArray(List<Double> list){
		double totalRate = 0;
		if(list == null || list.isEmpty()) return true;
		
		for(int i = 0 ; i < list.size() ; i++){
			double rate = list.get(i);
			if(rate < 0){
				return false;
			}
			totalRate = add(totalRate , rate);
		}
		
		if(totalRate != 100){
			return false;
		}
		
		return true;
	}
	
}
