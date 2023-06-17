package com.tiagoamp.shoppingcart.service;

import com.tiagoamp.shoppingcart.domain.Cart;
import com.tiagoamp.shoppingcart.domain.Item;
import com.tiagoamp.shoppingcart.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private IntegrationService integrationService;

    public Cart purchase(Cart shoppingCart) {
        var uuid = UUID.randomUUID().toString();

        shoppingCart.setId(uuid);
        UserInfo user = integrationService.getRemoteUserInfo(shoppingCart.getUser().getId());
        log.info("1 purchase - usr Id {}", shoppingCart.getUser().getId());
        shoppingCart.setUser(user);

        List<Item> items = integrationService.getRemoteProductItemsInfo(shoppingCart.getItems());
        shoppingCart.setItems(items);
        log.info("2 purchase = items");

//        integrationService.submitToBilling(shoppingCart);
//        integrationService.notifyToDelivery(shoppingCart);
//        integrationService.askForUserReview(shoppingCart);

        return shoppingCart;
    }
}
