package com.muru.dcb.reactive.demo.services;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxServices {
    public Flux<String> getFruits() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon")).log();
    }
    public Flux<String> getFruits_transform() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .transform(flux->flux.map(n->n.substring(0,3))).log();
    }

    public Flux<String> getFruits_transform_default() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .transform(flux->flux.filter(n->n.length()>25))
                .defaultIfEmpty("No Matches").log();
    }

    public Flux<String> getFruits_transform_switch_if() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .transform(flux->flux.filter(n->n.length()>25))
                .switchIfEmpty(Flux.just("Plums", "Dates")).log();
    }

    public Flux<String> getFruitsAndVeg() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans"));
        return  Flux.concat(fruits,veg).log();
    }

    public Flux<String> getFruitsAndVeg_concatWith() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans"));
        return  fruits.concatWith(veg).log();
    }

    public Flux<String> getFruitsAndVeg_merge() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .delayElements(Duration.ofMillis(35));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans", "Lemon", "Chilli"));
        return  Flux.merge(fruits,veg).log();
    }

    public Flux<String> getFruitsAndVeg_mergeWith() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .delayElements(Duration.ofMillis(35));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans", "Lemon", "Chilli"))
                .delayElements(Duration.ofMillis(75));
        return  fruits.mergeWith(veg).log();
    }

    public Flux<String> getFruitsAndVeg_mergeSequential() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .delayElements(Duration.ofMillis(35));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans", "Lemon", "Chilli"))
                .delayElements(Duration.ofMillis(75));
        return  Flux.mergeSequential(fruits, veg).log();
    }

    public Flux<String> getFruitsInUpper() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .map(String::toUpperCase);
    }
    public Flux<String> getFruitNameChars() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .flatMap(f -> Flux.just(f.split(""))).log();
    }

    public Flux<String> getFruitNameChars_ConcatMap() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .concatMap(f -> Flux.just(f.split(""))).log();
    }

    public Flux<String> getFruitsWithDelay() {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .delayElements(Duration.ofMillis(new Random().nextInt(255))).log();
    }

    public Flux<String> getFruits(int length) {
        return Flux.fromIterable(List.of("Mango","Banana", "Watermelon"))
                .filter(f->f.length() > length);
    }

    public Flux<String> getFruitsAndVeg_Zip() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans"));
        return  Flux.zip(fruits, veg, (f, v) -> f + "-" + v).log();
    }

    public Flux<String> getFruitsAndVeg_ZipWith() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans"));
        return  fruits.zipWith(veg, (f, v) -> f + "-" + v).log();
    }

    public Flux<String> getFruitsAndVeg_ZipTuple() {
        var fruits = Flux.fromIterable(List.of("Mango","Banana", "Watermelon"));
        var veg = Flux.fromIterable(List.of("Tomato","Onion", "Beans"));
        var cheese = Flux.fromIterable(List.of("Marmalade","Cottage", "Irish"));
        return  Flux.zip(fruits, veg, cheese)
                .map(z -> z.getT1() + "-" + z.getT2() + "-" + z.getT3())
                .log();
    }

    public Flux<String> getFruitsAndVeg_doOn() {
        return Flux.fromIterable(List.of("Mango", "Banana", "Watermelon"))
                .doOnNext(v-> System.out.println("value = " + v))
                .doOnSubscribe(s-> System.out.println("subscription = " + s))
                .doOnComplete(() -> System.out.println("Flux completed"))
                .log();

    }

    public Flux<String> getFruitsAndVeg_OnErrorReturn() {
        return Flux.fromIterable(List.of("Mango", "Banana", "Watermelon"))
                .concatWith(Flux.error(new RuntimeException("Custom error")))
                .onErrorReturn("Orange")
                .log();
    }

    public Flux<String> getFruitsAndVeg_OnErrorContinue() {
        return Flux.fromIterable(List.of("Mango", "Banana", "Watermelon"))
                .map(v-> {
                    if (v.equals("Banana")) throw new RuntimeException("Custom Error");
                    return v.toUpperCase();
                })
                .onErrorContinue((err,val)->{
                    System.out.println("err = " + err);
                    System.out.println("val = " + val);
                })
                .log();
    }

    public Flux<String> getFruitsAndVeg_OnErrorMap() {
        return Flux.fromIterable(List.of("Mango", "Banana", "Watermelon"))
                .map(v-> {
                    if (v.equals("Banana")) throw new RuntimeException("Custom Error");
                    return v.toUpperCase();
                })
                .onErrorMap(throwable->{
                    System.out.println("err = " + throwable);
                    throw new IllegalStateException("custom error");
                })
                .log();
    }

    public Flux<String> getFruitsAndVeg_doOnError() {
        return Flux.fromIterable(List.of("Mango", "Banana", "Watermelon"))
                .checkpoint("check point 1")
                .map(v-> {
                    if (v.equals("Banana")) throw new RuntimeException("Custom Error");
                    return v.toUpperCase();
                })
                .checkpoint("check point 2")
                .doOnError(throwable->{
                    System.out.println("err = " + throwable);
                })
                .log();
    }

    public static void main(String[] args) {
        FluxServices fluxServices = new FluxServices();
        fluxServices.getFruits().subscribe(f-> System.out.println("Flux Fruit = " + f));
    }

}
