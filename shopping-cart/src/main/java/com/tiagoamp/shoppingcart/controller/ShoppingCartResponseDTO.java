package com.tiagoamp.shoppingcart.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartResponseDTO {
    private String id;
    private Long userId;
    private String userName;
    private List<ItemDTO> items;
    private BigDecimal totalPrice;
}