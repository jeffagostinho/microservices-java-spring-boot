package br.com.jeffagostinho.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "Foobar endpoint")
@RestController
@RequestMapping("/books")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @Operation(summary = "Get foo bar")
    @GetMapping(value = "/foo-bar")
//    @Retry(name = "foo-bar", fallbackMethod = "fallbackFooBar")
//    @CircuitBreaker(name = "default", fallbackMethod = "fallbackFooBar")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String fooBar()  {
        logger.info("Request foo bar!");
//        var response = new RestTemplate()
//                .getForEntity("http://localhost:8080/foo-bar", String.class);
//        return response.getBody();

        return "foo-bar";
    }

    public String fallbackFooBar(Exception e) {
        logger.info("Fallback method called because of {}", e.getMessage());
        return "Fallback Foo Bar!!!";
    }
}
