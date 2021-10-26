package com.muru.dcb.reactive.demo.services;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackPressureTest {
    @Test
    public void backPressure() {
        var numbers = Flux.range(1, 100);
        numbers.subscribe(n-> System.out.println("number = " + n));
    }

    @Test
    public void backPressure_BaseSub() {
        var numbers = Flux.range(1, 100).log();
        numbers.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if (value == 3) cancel();
            }
        });
    }

    @Test
    public void backPressure_Drop() {
        var numbers = Flux.range(1, 100).log();
        numbers.onBackpressureDrop(integer -> System.out.println("Dropped Value = " + integer))
                .subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if (value == 3) hookOnCancel();
            }
        });
    }

    @Test
    public void backPressure_Buffer() {
        var numbers = Flux.range(1, 100).log();
        numbers.onBackpressureBuffer(10, integer -> System.out.println("Buffered Value = " + integer))
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(3);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("value = " + value);
                    if (value == 3) hookOnCancel();
                }
            });
    }

    @Test
    public void backPressure_Error() {
        var numbers = Flux.range(1, 100).log();
        numbers.onBackpressureError()
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(3);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("value = " + value);
                    if (value == 3) hookOnCancel();
                }

                @Override
                protected void hookOnError(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                }
            });
    }
}
