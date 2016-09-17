package com.klutzer.wineshop.utils;

import java.math.BigDecimal;

import com.klutzer.wineshop.business.Money;

public class ValuesUtils {

	public static Money money(BigDecimal bg) {
		return Money.valueOf(bg);
	}
	
	public static Money money(double value) {
		return Money.valueOf(bg(value));
	}
	
	public static BigDecimal bg(double v) {
		return BigDecimal.valueOf(v);
	}
	
}
