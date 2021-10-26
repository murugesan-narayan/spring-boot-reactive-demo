package com.muru.dcb.reactive.demo.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import reactor.tools.agent.ReactorDebugAgent;

import static org.junit.jupiter.api.Assertions.*;

class FluxServicesTest {
    FluxServices fluxServices = new FluxServices();
    @Test
    void getFruits() {
        Flux<String> fruits = fluxServices.getFruits();
        StepVerifier.create(fruits).expectNext("Mango","Banana", "Watermelon")
                .verifyComplete();
    }

    @Test
    void getFruitsInUpper() {
        Flux<String> fruits = fluxServices.getFruitsInUpper();
        StepVerifier.create(fruits).expectNext("MANGO","BANANA", "WATERMELON")
                .verifyComplete();
    }

    @Test
    void getFruitsWithNameLengthGraterThanSpecified() {
        Flux<String> fruits = fluxServices.getFruits(5);
        StepVerifier.create(fruits).expectNext("Banana", "Watermelon")
                .verifyComplete();
    }

    @Test
    void getFruitNameChars() {
        Flux<String> fruits = fluxServices.getFruitNameChars();
        StepVerifier.create(fruits).expectNextCount(21)
                .verifyComplete();
    }

    @Test
    void getFruitsWithDelay() {
        Flux<String> fruits = fluxServices.getFruitsWithDelay();
        StepVerifier.create(fruits).expectNext("Mango","Banana", "Watermelon")
                .verifyComplete();
    }

    @Test
    void getFruitNameChars_ConcatMap() {
        Flux<String> fruits = fluxServices.getFruitNameChars_ConcatMap();
        StepVerifier.create(fruits).expectNextCount(21)
                .verifyComplete();
    }

    @Test
    void getFruits_transform() {
        Flux<String> fruits = fluxServices.getFruits_transform();
        StepVerifier.create(fruits).expectNext("Man","Ban", "Wat")
                .verifyComplete();
    }

    @Test
    void getFruits_transform_default() {
        Flux<String> fruits = fluxServices.getFruits_transform_default();
        StepVerifier.create(fruits).expectNext("No Matches")
                .verifyComplete();
    }

    @Test
    void getFruits_transform_switch_if() {
        Flux<String> fruits = fluxServices.getFruits_transform_switch_if();
        StepVerifier.create(fruits).expectNext("Plums", "Dates")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg();
        StepVerifier.create(fruits)
                .expectNext("Mango","Banana", "Watermelon",
                        "Tomato","Onion", "Beans")
                .verifyComplete();
    }
    @Test
    void getFruitsAndVeg_concatWith() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_concatWith();
        StepVerifier.create(fruits)
                .expectNext("Mango","Banana", "Watermelon",
                        "Tomato","Onion", "Beans")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_merge() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_merge();
        StepVerifier.create(fruits).expectNextCount(8)
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_mergeWith() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_mergeWith();
        StepVerifier.create(fruits).expectNextCount(8)
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_mergeSequential() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_mergeSequential();
        StepVerifier.create(fruits)
                .expectNext("Mango","Banana", "Watermelon",
                        "Tomato","Onion", "Beans", "Lemon", "Chilli")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_Zip() {
        Flux<String> zip = fluxServices.getFruitsAndVeg_Zip();
        StepVerifier.create(zip)
                .expectNext("Mango-Tomato","Banana-Onion", "Watermelon-Beans")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_ZipWith() {
        Flux<String> zip = fluxServices.getFruitsAndVeg_ZipWith();
        StepVerifier.create(zip)
                .expectNext("Mango-Tomato","Banana-Onion", "Watermelon-Beans")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_ZipTuple() {
        Flux<String> zip = fluxServices.getFruitsAndVeg_ZipTuple();
        StepVerifier.create(zip)
                .expectNext("Mango-Tomato-Marmalade",
                        "Banana-Onion-Cottage",
                        "Watermelon-Beans-Irish")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_doOn() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_doOn();
        StepVerifier.create(fruits)
                .expectNext("Mango","Banana", "Watermelon")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_OnErrorReturn() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_OnErrorReturn();
        StepVerifier.create(fruits)
                .expectNext("Mango","Banana", "Watermelon", "Orange")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_OnErrorContinue() {
        Flux<String> fruits = fluxServices.getFruitsAndVeg_OnErrorContinue();
        StepVerifier.create(fruits)
                .expectNext("MANGO","WATERMELON")
                .verifyComplete();
    }

    @Test
    void getFruitsAndVeg_doOnError() {
        //Hooks.onOperatorDebug();
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
        Flux<String> fruits = fluxServices.getFruitsAndVeg_doOnError();
        StepVerifier.create(fruits)
                .expectNext("MANGO")
                .expectError(RuntimeException.class)
                .verify();
    }
}