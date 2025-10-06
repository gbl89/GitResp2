package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "userpoints")
public interface UserPointsClient {
    @GetMapping("/up")
    public void up();
}
