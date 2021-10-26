package com.muru.dcb.reactive.demo.services;

import com.muru.dcb.reactive.demo.domain.Book;
import com.muru.dcb.reactive.demo.exception.BookServiceException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
public class BookService {
    private final BookInfoService bookInfoService;
    private final ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var allBookInfo = bookInfoService.getBookInfo();
        return allBookInfo.flatMap(bookInfo -> {
            var allReviews = reviewService.getReviews(bookInfo.getId());
            return allReviews.map(reviews -> new Book(bookInfo, reviews));
        }).onErrorMap(throwable -> {
            log.error("Error thrown", throwable);
            throw new BookServiceException("Service error");
        }).retry(3).log();
    }

    public Flux<Book> getBooksWithRetryWhen() {
        var retrySpec = Retry.backoff(3, Duration.ofMillis(2000))
                .filter(throwable -> throwable instanceof BookServiceException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure()));
        var allBookInfo = bookInfoService.getBookInfo();
        return allBookInfo.flatMap(bookInfo -> {
            var allReviews = reviewService.getReviews(bookInfo.getId());
            return allReviews.map(reviews -> new Book(bookInfo, reviews));
        }).onErrorMap(throwable -> {
            log.error("Error thrown", throwable);
            throw new BookServiceException("Service error");
        }).retryWhen(retrySpec).log();
    }

    public Mono<Book> getBookById(long id) {
        var allBookInfo = bookInfoService.getBookInfoById(id);
        var reviews = reviewService.getReviews(id);
        return allBookInfo
                .zipWith(reviews, Book::new)
                .log();
    }
}
