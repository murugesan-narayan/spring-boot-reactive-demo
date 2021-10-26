package com.muru.dcb.reactive.demo.services;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonoServices {
    public Mono<String> getFruit() {
        return Mono.just("Mango").log();
    }

    public Mono<List<String>> getFruitNameChars() {
        return Mono.just("Mango").flatMap(s->Mono.just(List.of(s.split("")))).log();
    }

    public Flux<String> getFruitNameChars_FlatMapMany() {
        return Mono.just("Mango").flatMapMany(s->Flux.just(s.split(""))).log();
    }

    public Flux<String> getFruits_concat() {
        return Mono.just("Mango")
                .concatWith(Mono.just("Orange"))
                .log();
    }

    public Mono<String> getFruitsAndVeg_Zip() {
        var fruits = Mono.just("Mango");
        var veg = Mono.just("Tomato");
        return  Mono.zip(fruits, veg, (f, v) -> f + "-" + v).log();
    }

    public static void main(String[] args) {
        MonoServices monoServices = new MonoServices();
        monoServices.getFruit().subscribe(f-> System.out.println("Mono Fruit = " + f));
    }
}
