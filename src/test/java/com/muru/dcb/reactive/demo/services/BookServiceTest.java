package com.muru.dcb.reactive.demo.services;

import com.muru.dcb.reactive.demo.domain.Book;
import com.muru.dcb.reactive.demo.domain.BookInfo;
import com.muru.dcb.reactive.demo.domain.Review;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookInfoService bookInfoService = new BookInfoService();
    private ReviewService reviewService = new ReviewService();
    private BookService bookService = new BookService(bookInfoService, reviewService);
    @Test
    void getBooks() {
        var books = bookService.getBooks();
        StepVerifier.create(books).expectNext(
                new Book(
                    new BookInfo(1, "Thirukral", "Thiruvalluvar", "isbn12333378"),
                    List.of(new Review(1, 1, 10.0, "Must Read"),
                            new Review(1, 1, 9.7, "Life Kit"))),
                new Book(
                    new BookInfo(2, "Periya Purana", "Chekkilar", "isbn12793678"),
                    List.of(new Review(2, 2, 9.5, "Worth Read"))),
                new Book(
                     new BookInfo(3, "Kambar Ramayana", "Kambar", "isbn12981278"),
                        List.of( new Review(3, 3, 8.0, "Amazing story")))
            ).verifyComplete();
    }

    @Test
    void getBookById() {
        var book = bookService.getBookById(1L);
        StepVerifier.create(book).expectNext(
                new Book(
                        new BookInfo(1, "Thirukral", "Thiruvalluvar", "isbn12333378"),
                        List.of(new Review(1, 1, 10.0, "Must Read"),
                                new Review(1, 1, 9.7, "Life Kit")))
        ).verifyComplete();
    }
}