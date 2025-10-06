package com.mashibing.controller;

import com.mashibing.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceOrderController {

    @Autowired
    private ItemStockClient itemStockClient;
    @Autowired
    private OrderManageClient orderManageClient;
    @Autowired
    private CouponClient couponClient;
    @Autowired
    private UserPointsClient userPointsClient;
    @Autowired
    private BusinessClient businessClient;

    /*
    * 模拟用户下单操作
    * */
    @GetMapping("/po")
    public String po(){
        long start = System.currentTimeMillis();
        //1.调用库存服务扣除商品的库存
        itemStockClient.decr();
        //2.调用订单服务，创建订单
        orderManageClient.create();
        //3.调用优惠价服务，预扣除使用的优惠劵
        couponClient.coupon();
        //4.调用用户积分服务，预扣除用户使用的积分
        userPointsClient.up();
        //5.调用商家服务，通知商家用户已经下单
        businessClient.notifyBusiness();
        long end =System.currentTimeMillis();
        System.out.println("使用的时长为"+(end-start));
        return "place order is ok!";
    }
}
