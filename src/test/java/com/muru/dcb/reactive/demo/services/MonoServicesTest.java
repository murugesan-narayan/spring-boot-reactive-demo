package com.muru.dcb.reactive.demo.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonoServicesTest {
    MonoServices monoServices = new MonoServices();
    @Test
    void getFruit() {
        Mono<String> fruit = monoServices.getFruit();
        StepVerifier.create(fruit).expectNext("Mango").verifyComplete();
    }

    @Test
    void getFruitsAndVeg_Zip() {
        var fruits = monoServices.getFruitsAndVeg_Zip();
        StepVerifier.create(fruits).expectNext("Mango-Tomato").verifyComplete();
    }


    @Test
    void getFruitNameChars() {
        Mono<List<String>> fruit = monoServices.getFruitNameChars();
        StepVerifier.create(fruit).expectNextCount(1).verifyComplete();
    }

    @Test
    void getFruitNameChars_FlatMapMany() {
        Flux<String> fruit = monoServices.getFruitNameChars_FlatMapMany();
        StepVerifier.create(fruit).expectNextCount(5).verifyComplete();
    }

    @Test
    void getFruits_concat() {
        var fruits = monoServices.getFruits_concat();
        StepVerifier.create(fruits).expectNext("Mango", "Orange").verifyComplete();
    }
}