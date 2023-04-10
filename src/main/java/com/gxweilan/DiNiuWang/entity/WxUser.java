package com.gxweilan.DiNiuWang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 14:14
 */
@Data
public class WxUser {

    /**
     * id主键
     */
    private Integer id;
    /**
     * 微信获取的姓名
     */
    private String nikeName;
    /**
     * 微信的客户姓名
     */
    private String userName;
    /**
     * 与转让公司构建的电话主键
     */
    private String companyPhone;
    /**
     * 微信获取的手机号码
     */
    private Long ordering;
    /**
     *
     */
    private Long orderAmount;
    /**
     * web端输入的当前微信人员的公司名称
     */
    private String myCompany;
    /**
     * 创立时间
     */
    private String createtime;
    /**
     * 公司创立时间
     */
    private String companyCreateTime;
    /**
     * 纳税类型（小规模纳税人、一般纳税人）
     */
    private String taxPaymentType;
    /**
     * 代账期限区间
     */
    private String limitTime;
    /**
     * 服务项目
     */
    private String serviceItem;
    /**
     * 缴费总额
     */
    private String priceAmount;
    /**
     * 客服电话
     */
    private String servicePhone;
    /**
     * 财务顾问
     */
    private String financialAdviser;

    /**
     * 获取的openid
     */
    private String openid;
    /**
     * 通过md5算法加密过后的openid
     */
    private String md5Openid;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatraUrl;
    /**
     * 登录最新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String details;
    /**
     * 国籍
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;

}