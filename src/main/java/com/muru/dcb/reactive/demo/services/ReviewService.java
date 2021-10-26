package com.muru.dcb.reactive.demo.services;

import com.muru.dcb.reactive.demo.domain.BookInfo;
import com.muru.dcb.reactive.demo.domain.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class ReviewService {
    private Map<Long, List<Review>> reviews = Map.of(
            1L, List.of(
                    new Review(1, 1, 10.0, "Must Read"),
                    new Review(1, 1, 9.7, "Life Kit")),
            2L, List.of(new Review(2, 2, 9.5, "Worth Read")),
            3L, List.of( new Review(3, 3, 8.0, "Amazing story"))
    );

    public Mono<List<Review>> getReviews(long bookId) {

        return Mono.just(reviews.get(bookId));
    }

}
