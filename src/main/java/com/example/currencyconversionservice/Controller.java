package com.example.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

	@GetMapping("get-currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriOptions = new HashMap<>();
		uriOptions.put("from", from);
		uriOptions.put("to", to);
		//using resttemplate to make call to other api
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(
				"http://localhost:8000/get-currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
				uriOptions);
		CurrencyConversion currencyConversion = response.getBody();
		CurrencyConversion cc = new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(),
				currencyConversion.getTo(), currencyConversion.getConversionMultiple(), quantity,
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());

		return cc;

	}

}
