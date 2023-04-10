package com.gxweilan.DiNiuWang.query;

import lombok.Data;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 16:08
 */
@Data
public class CompanyTransferQuery {
    /**
     * 转让公司的企业编号
     */
    private String companyCode;
    /**
     * 转让的公司名
     */
    private String companyName;
    /**
     * 企业行业
     */
    private String companyForward;
    /**
     * 公司基本信息的工商信用代码
     */
    private String companyGSCode;
    /**
     * 企业类型
     */
    private String companyType;
    /**
     * 纳税类型
     */
    private String taxPaymentType;
    /**
     * 登记机关
     */
    private String registrationAuthority;
    /**
     * 公司基本信息的经营时间
     */
    private String companyOpTime;
    /**
     * 注册资本
     */
    private String registeredCapital;
    /**
     * 公司基本信息的无形资产
     */
    private String intangibleAssets;
    /**
     * 公司基本信息的是否开户
     */
    private Boolean isOpenAccount;
    /**
     * 公司基本信息的是否带发票
     */
    private Boolean isHaveInvoice;
    /**
     * 公司基本信息的是否带网银
     */
    private Boolean isHaveEBanking;
    /**
     * 公司所在地
     */
    private String companyAddress;
    /**
     * 公司基本信息的经营范围
     */
    private String companyDetails;
    /**
     * 公司联系电话
     */
    private String companyPhone;
    /**
     * 公司企业顾问电话
     */
    private String enterpriseConsultant;

}
