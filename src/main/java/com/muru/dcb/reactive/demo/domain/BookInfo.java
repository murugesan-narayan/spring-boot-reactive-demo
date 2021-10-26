package com.muru.dcb.reactive.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {
    private long id;
    private String title;
    private String author;
    private String isbn;
}
