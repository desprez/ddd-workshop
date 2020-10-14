package com.ddd.training.application;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

// Simulation d'une librairie externe
@Component
public class DataService {

	public BigDecimal getCrossChange(final Date date) {
		return BigDecimal.ONE;
	}
}
