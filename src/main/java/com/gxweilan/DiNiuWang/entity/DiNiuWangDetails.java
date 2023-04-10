package com.gxweilan.DiNiuWang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 10:40
 */
@Data
@AllArgsConstructor
public class DiNiuWangDetails {
    /**
     * 通过当前登录的微信人员获取的openId
     */
    private String openId;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 收款方
     */
    private String payee;
    /**
     * 服务项目
     */
    private String serviceItem;
    /**
     * 价格
     */
    private String money;
    /**
     * 0表示未缴费，1表示已缴费
     */
    private Boolean wasPaid;
}
