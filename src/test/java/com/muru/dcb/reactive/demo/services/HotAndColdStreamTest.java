package com.muru.dcb.reactive.demo.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStreamTest {
    @Test
    public void coldStream() {
        var numbers = Flux.range(1, 10);
        numbers.subscribe((i-> System.out.println("sub-1 = " + i)));
        numbers.subscribe((j-> System.out.println("sub-2 = " + j)));
    }

    @SneakyThrows
    @Test
    public void hotStream() {
        var numbers = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        ConnectableFlux<Integer> publisher = numbers.publish();
        publisher.connect();
        publisher.subscribe((i-> System.out.println("sub-1 = " + i)));
        Thread.sleep(5000);
        publisher.subscribe((j-> System.out.println("sub-2 = " + j)));
        Thread.sleep(10000);
    }
}
