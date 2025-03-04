package br.com.jeffagostinho.controller;

import br.com.jeffagostinho.model.Book;
import br.com.jeffagostinho.proxy.ExchangeProxy;
import br.com.jeffagostinho.repository.BookRepository;
import br.com.jeffagostinho.response.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExchangeProxy exchangeProxy;

    @GetMapping(value = "/{id}/{currency}")
    public Book getBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find book for id: " + id));


        var exchange = exchangeProxy.getExchange(
                book.getPrice(),
                "USD",
                currency
        );

        String port = environment.getProperty("local.server.port");

        book.setEnvironment(port + " feign");
        book.setCurrency(currency);
        book.setPrice(exchange.getConvertedValue());

        return book;
    }

    @GetMapping(value = "/rest/{id}/{currency}")
    public Book getBookResTemplate(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find book for id: " + id));

        HashMap<String, String> params = new HashMap<>();

        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().getForEntity(
                "http://localhost:8000/exchange/{amount}/{from}/{to}",
                Exchange.class,
                params
        );

        var exchange = response.getBody();

        String port = environment.getProperty("local.server.port");

        book.setEnvironment(port);
        book.setCurrency(currency);
        book.setPrice(exchange.getConvertedValue());

        return book;
    }
}
