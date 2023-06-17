package com.tiagoamp.shoppingcart.controller;


import com.tiagoamp.shoppingcart.domain.Cart;
import com.tiagoamp.shoppingcart.service.CartMapper;
import com.tiagoamp.shoppingcart.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    private ShoppingCartService service;
    private CartMapper mapper;

    public ShoppingCartController(ShoppingCartService service, CartMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/cart")
    public ResponseEntity<ShoppingCartResponseDTO> submit(@RequestBody ShoppingCartRequestDTO requestDTO) {
        log.info("START ");
        Cart cart = mapper.toModel(requestDTO);
        cart = service.purchase(cart);
        ShoppingCartResponseDTO responseDTO = mapper.toResponseDTO(cart);

        return ResponseEntity.created(URI.create(responseDTO.getId())).body(responseDTO);
    }
}
