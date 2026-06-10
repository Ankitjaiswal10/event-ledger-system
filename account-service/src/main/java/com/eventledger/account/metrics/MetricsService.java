package com.eventledger.account.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricsService {

    private final Counter counter;

    public MetricsService(MeterRegistry registry) {

        this.counter =
                Counter.builder(
                                "account_transactions_total")
                        .register(registry);
    }

    public void increment() {
        counter.increment();
    }
}
