package com.muru.dcb.reactive.demo.services;

import com.muru.dcb.reactive.demo.domain.BookInfo;
import com.muru.dcb.reactive.demo.exception.BookServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {
    @Mock
    private BookInfoService bookInfoService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBooks() {
        Mockito.when(bookInfoService.getBookInfo())
                .thenReturn(Flux.just(new BookInfo(1,"test", "test","2323")));

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("exception using test"));

        var books = bookService.getBooks();

        StepVerifier.create(books)
                .expectError(BookServiceException.class)
                .verify();
    }

    @Test
    void getBooksWithRetryWhen() {
        Mockito.when(bookInfoService.getBookInfo())
                .thenReturn(Flux.just(new BookInfo(1,"test", "test","2323")));

        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("exception using test"));

        var books = bookService.getBooksWithRetryWhen();

        StepVerifier.create(books)
                .expectError(BookServiceException.class)
                .verify();
    }
}