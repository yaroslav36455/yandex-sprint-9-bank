package by.tyv.exchangegenerator.scheduler;

import by.tyv.exchangegenerator.service.ExchangeRateGeneratorService;
import by.tyv.exchangegenerator.service.ExchangeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduler.enable", havingValue = "true")
public class Scheduler {

    private final ExchangeRateGeneratorService generatorService;
    private final ExchangeService exchangeService;
    private Disposable task;

    @PostConstruct
    public void generateExchangeRates() {
        task = Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> generatorService.generate())
                .flatMap(exchangeService::update)
                .subscribe();
    }

    @PreDestroy
    public void preDestroy() {
        if (task != null) {
            task.dispose();
        }
    }
}
