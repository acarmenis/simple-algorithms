package com.zinkworks.domain.dto;


import lombok.*;

/**
 * request client's money wrapper
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Money {
    // money
    private int amount;
}
