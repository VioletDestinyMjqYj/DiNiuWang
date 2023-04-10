package com.gxweilan.DiNiuWang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Projectname: DiNiuWang
 * @Filename: Order
 * @Author: 墨杨清竹
 * @Data:2023/2/20 10:33
 * @Description: TODO
 */
@Data
@TableName("order")
public class Order {


    /**
     * 主键id
     */
    private Integer id;
    /**
     * 支付单号
     */
    private String payNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 第三方流水号
     */
    private String outTradeNo;
    /**
     * openid
     */
    private String openid;
    /**
     * 支付渠道：1微信 2支付宝（暂定1）
     */
    private String payChannel;
    /**
     * 支付子渠道：11微信公众号支付 12扫码支付
     */
    private String subPayChannel;
    /**
     * 支付金额
     */
    private String payAmount;
    /**
     * 实际支付金额
     */
    private String realPayAmount;
    /**
     * 买的商品名称
     */
    private String body;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 退款金额
     */
    private String refundAmount;
    /**
     * 交易状态：1待支付，2已支付，3退款中，4已退款，5支付中
     */
    private String status;
    /**
     * 退款时间
     */
    private String refundTime;
    /**
     * 失败原因
     */
    private String failReason;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
