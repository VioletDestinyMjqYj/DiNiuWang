package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.gxweilan.DiNiuWang.entity.Order;
import com.gxweilan.DiNiuWang.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/8 9:14
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public int createOrder(Order order) {
        if (order == null){
            return 0;
        }
        return orderMapper.createOrder(order);
    }

    @Override
    public int getByTradeNo(String outTradeNo) {
        if (outTradeNo == null || outTradeNo.length() == 0){
            return 0;
        }
        return orderMapper.getByTradeNo(outTradeNo);
    }

    @Override
    public String updateOrderStatus(Integer id, String status) {
        if (status == null || id == null){
            return null;
        }
        return orderMapper.updateOrderStatus(id, status);
    }

    @Override
    public String createWxOrder(Order order) {
        if (order == null) {
            return null;
        }
        return orderMapper.createWxOrder(order);
    }
}
