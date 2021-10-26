package com.muru.dcb.reactive.demo.services;

import com.muru.dcb.reactive.demo.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class BookInfoService {
    private Map<Long, BookInfo> books = Map.of(
            1L, new BookInfo(1, "Thirukral", "Thiruvalluvar", "isbn12333378"),
            2L, new BookInfo(2, "Periya Purana", "Chekkilar", "isbn12793678"),
            3L, new BookInfo(3, "Kambar Ramayana", "Kambar", "isbn12981278")
    );

    public Flux<BookInfo> getBookInfo() {

        return Flux.fromIterable(books.values());
    }

    public Mono<BookInfo> getBookInfoById(long id) {
        return Mono.just(books.get(id));
    }
}
