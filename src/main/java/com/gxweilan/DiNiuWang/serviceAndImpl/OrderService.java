package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.gxweilan.DiNiuWang.entity.Order;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 15:53
 */
//extends IService<Order>
public interface OrderService  {
    int createOrder(Order order);

    int getByTradeNo(String outTradeNo);

    String updateOrderStatus(Integer id, String status);

    String createWxOrder(com.gxweilan.DiNiuWang.entity.Order order);
}
