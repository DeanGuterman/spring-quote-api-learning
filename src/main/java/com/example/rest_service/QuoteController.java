package com.example.rest_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class QuoteController{

    private static final AtomicLong counter = new AtomicLong();
    private static final List<Quote> quotes = new ArrayList<>();

    @GetMapping("/quotes")
    public List<Quote> getAllQuotes(){
        return quotes;
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable long id){
        return quotes.stream().filter(quote -> quote.id() == id).findFirst().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/quotes")
    public Quote addQuote(@RequestBody Quote quote){
        Quote newQuote = new Quote(counter.incrementAndGet(), quote.author(), quote.content());
        quotes.add(newQuote);
        return newQuote;
    }

    @DeleteMapping("/quotes")
    public boolean deleteQuote(@RequestParam(value = "id") long id){
        return quotes.removeIf(curr -> curr.id() == id);
    }
}
