package com.muru.dcb.reactive.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private BookInfo bookInfo;
    private List<Review> reviews;
}
