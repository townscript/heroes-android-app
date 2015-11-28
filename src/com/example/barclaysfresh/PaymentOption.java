package com.example.barclaysfresh;

public enum PaymentOption {
	
	PAYU_MONEY,
	
	CITRUS_PAY,
	
	CREDIT_AND_DEBIT_CARD;
	
	public static PaymentOption getValue(String input) {
		
		if(input.equalsIgnoreCase("payumoney")) {
			return PAYU_MONEY;
		}
		
		if(input.equalsIgnoreCase("citruspay")) {
			return CITRUS_PAY;
		}
		
		if(input.equalsIgnoreCase("ccdbcard")) {
			return CREDIT_AND_DEBIT_CARD;
		}
		
		throw new IllegalStateException("There is no PaymentOption code with value - " + input);
	}

}
