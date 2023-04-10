package com.gxweilan.DiNiuWang.mapper;

import com.gxweilan.DiNiuWang.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Projectname: DiNiuWang
 * @Filename: OrderMapper
 * @Author: 墨杨清竹
 * @Data:2023/2/20 10:51
 * @Description: TODO
 */
@Mapper
public interface OrderMapper{

    int createOrder(Order order);

    int getByTradeNo(String outTradeNo);

    String updateOrderStatus(Integer id, String status);


    String createWxOrder(com.gxweilan.DiNiuWang.entity.Order order);
}
