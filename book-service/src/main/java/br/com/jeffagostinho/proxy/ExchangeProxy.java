package br.com.jeffagostinho.proxy;

import br.com.jeffagostinho.response.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-service")
public interface ExchangeProxy {

    @GetMapping("/exchange/{amount}/{from}/{to}")
    public Exchange getExchange(
            @PathVariable("amount") Double amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );
}
