package com.stockservice.demo.dbservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockservice.demo.dbservice.model.Quote;
import com.stockservice.demo.dbservice.model.Quotes;
import com.stockservice.demo.dbservice.repository.QuotesRepository;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResources {
    
	@Autowired
	private QuotesRepository repo;
	
	@GetMapping("/{username}")
	public List<String> getQuotes(@PathVariable  final String username ){
		
		return  getQuotesByUserName(username);
		
	}
	
	public List<String> getQuotesByUserName(@PathVariable("username")  final String username ){
		return repo.findByUserName(username).stream().map(Quote::getQuote).collect(Collectors.toList());
		
	}
	
	@PostMapping("/add")
	public List<String> add(@RequestBody Quotes quotes){
		  quotes.getQuotes().stream()
		  .map(quote->new Quote(quotes.getUserName(),quote))
		  .forEach(quote->repo.save(quote));
		  return getQuotesByUserName(quotes.getUserName());
	}
	
	
	@PostMapping("/delete/{username}")
	public List<String>  delete(@PathVariable  String username){
		List<Quote> quotes=repo.findByUserName(username);
		repo.delete(new Quote(username,"sudheer"));
		return getQuotesByUserName(username);
	}
}
