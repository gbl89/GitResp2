package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "itemstock")
public interface ItemStockClient {
    @GetMapping("/decr")
    public void decr();
}
