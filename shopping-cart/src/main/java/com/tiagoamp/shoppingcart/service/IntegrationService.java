package com.tiagoamp.shoppingcart.service;

import com.tiagoamp.shoppingcart.domain.Item;
import com.tiagoamp.shoppingcart.domain.ProductOverview;
import com.tiagoamp.shoppingcart.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class IntegrationService {

    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;

    private final CircuitBreakerFactory circuitBreakerFactory;

    //NEW
    public UserInfo getRemoteUserInfo(Long userId) {
        // wrap in circuit breaker for fault tolerance
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        UserInfo user = circuitBreaker.run(() -> userFeignClient.findById(userId),
                throwable -> this.findUserByIdFallBack(userId));
        return user;
    }

    // fallback: returns default object
    private UserInfo findUserByIdFallBack(Long userId) {
        return new UserInfo(userId, "name (user) info unavailable", null, null);
    }

    //OLD - no circuit breaker
//    public UserInfo getRemoteUserInfo(Long userId) {
//        log.info("getRemoteUserInfo = userId {}", userId);
//        UserInfo user = userFeignClient.findById(userId);
//        return user;
//    }

    //NEW
    public List<Item> getRemoteProductItemsInfo(List<Item> items) {
        items.forEach(item -> {
            // wrap in circuit breaker for fault tolerance
            CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
            var product = circuitBreaker.run(() -> productFeignClient.findById(item.getProduct().getId()),
                    throwable -> this.findProductByIdFallBack(item.getProduct().getId()));

            item.setProduct(product);
        });
        return items;
    }

    private ProductOverview findProductByIdFallBack(Long id) {
        return new ProductOverview(id, "product name unavailable", null);
    }

    //OLD - no circuit breaker
//    public List<Item> getRemoteProductItemsInfo(List<Item> items) {
//        items.forEach(item -> {
//            ProductOverview product = productFeignClient.findById(item.getProduct().getId());
//            item.setProduct(product);
//        });
//        log.info("getRemoteProductItemsInfo - get(0) -  {}", items.get(0).getProduct().getId());
//        return items;
//    }
}