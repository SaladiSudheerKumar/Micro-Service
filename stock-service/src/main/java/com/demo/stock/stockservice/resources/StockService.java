package com.demo.stock.stockservice.resources;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@RestController
@RequestMapping("/rest/stock")
public class StockService {
	
	@Autowired
	RestTemplate restTemplate;
	

	
	@GetMapping("/{username}")
	public List<Stock> getALL(@PathVariable String username){
		
	 ResponseEntity<List<String>> quoteResponse=	restTemplate.exchange("http://localhost:8045/rest/db/"+username, HttpMethod.GET,null,new ParameterizedTypeReference<List<String>>() {
		});
	 
	 List<String> quotes=quoteResponse.getBody();
	 
	 return quotes.stream().map(this::getStockPrice).collect(Collectors.toList());
	}
	
	
	public Stock  getStockPrice(String quote) {
		 try {
				return YahooFinance.get(quote);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Stock(quote);
			}
		
	}

}
