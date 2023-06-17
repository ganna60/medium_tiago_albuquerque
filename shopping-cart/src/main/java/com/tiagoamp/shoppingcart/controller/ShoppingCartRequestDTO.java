package com.tiagoamp.shoppingcart.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartRequestDTO {
    private Long userId;
    private List<ItemDTO> items;
}