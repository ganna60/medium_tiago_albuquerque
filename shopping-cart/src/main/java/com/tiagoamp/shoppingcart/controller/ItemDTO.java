package com.tiagoamp.shoppingcart.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
}
