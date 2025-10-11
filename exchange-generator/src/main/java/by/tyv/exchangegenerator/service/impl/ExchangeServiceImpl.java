package by.tyv.exchangegenerator.service.impl;

import by.tyv.exchangegenerator.mapper.ExchangeMapper;
import by.tyv.exchangegenerator.model.bo.ExchangeRate;
import by.tyv.exchangegenerator.model.dto.ExchangeRateDto;
import by.tyv.exchangegenerator.service.ExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {
    private final WebClient webClient;
    private final ExchangeMapper exchangeMapper;

    public ExchangeServiceImpl(@Value("${clients.exchange-service.url}") String exchangeServiceUrl,
                               ExchangeMapper exchangeMapper,
                               WebClient.Builder webClientBuilder) {
        this.exchangeMapper = exchangeMapper;
        this.webClient = webClientBuilder
                .baseUrl(exchangeServiceUrl)
                .build();
    }

    @Override
    public Mono<Void> update(List<ExchangeRate> exchangeRates) {
        return webClient.post()
                .uri("/api/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(exchangeMapper.mapToDto(exchangeRates)), ExchangeRateDto.class)
                .exchangeToMono(ClientResponse::releaseBody)
                .onErrorContinue((throwable, obj) -> log.error("Ошибка при обновлении обмена валют {}", obj, throwable))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
