package com.muru.dcb.reactive.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private long id;
    private long bookId;
    private double ratings;
    private String comments;
}
