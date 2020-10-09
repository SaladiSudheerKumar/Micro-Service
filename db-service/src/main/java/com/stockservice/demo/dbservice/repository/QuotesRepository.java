package com.stockservice.demo.dbservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockservice.demo.dbservice.model.Quote;

public interface QuotesRepository  extends JpaRepository<Quote, Integer>{
	
	List<Quote> findByUserName(String username);

}
