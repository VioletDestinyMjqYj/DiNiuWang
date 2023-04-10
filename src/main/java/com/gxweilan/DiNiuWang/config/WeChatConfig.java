package com.gxweilan.DiNiuWang.config;

import com.gxweilan.DiNiuWang.Utils.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @Projectname: DiNiuWang
 * @Filename: WeChatConfig
 * @Author: 墨杨清竹
 * @Data:2023/2/17 15:11  最新版
 * @Description: TODO
 */
@Data
@Slf4j
@Configuration
public class WeChatConfig {
    /**
     * 预支付订单url
     */
    private String preOrderUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**
     * 商户号id
     */
    @Value("${wxpay.mchid}")
    private String mchid;

//    /**
//     * 微信支付回调url
//     */
//    @Value("${wxpay.payCallbackUrl}")
//    private String payCallbackUrl;

    /**
     * 微信appid
     */
    @Value("${wxpay.appid}")
    private String appid;

    /**
     * 微信APIV3支付证书
     */
    @Value("${wxpay.private-key-path}")
    private String certUrl;

    /**
     * 商家密钥
     */
    @Value("${wxpay.private-key-path}")
    private String privateKeyUrl;

    /**
     * 商家密钥
     */
    @Value("${wxpay.mch-serial-no}")
    private String mchSerialNo;

    /**
     * 通知地址
     */
    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;

    /**
     * appV3 key
     */
    @Value("${wxpay.api-v3-key}")
    private String appV3Key;

    /**
     * url
     */
    @Value("${wxpay.ToUrl}")
    private String ToUrl;

    /**
     * 测试的openid
     */
    @Value("${wxpay.openid}")
    private String openid;

    /**
     * 获取微信支付api V3支付证书
     * @return
     */
    public X509Certificate getCertificate() {
        try {
            return  PemUtil.loadCertificate(new FileInputStream(certUrl));
        } catch (FileNotFoundException e) {
            log.error("加载微信支付APIv3支付证书失败{}", e.getMessage(), e);
        }
        return null;
    }

    public PrivateKey getPrivateKey() {
        try {
            return  PemUtil.loadPrivateKey(new FileInputStream(privateKeyUrl));
        } catch (FileNotFoundException e) {
            log.error("加载微信支付APIv3支付证书失败{}", e.getMessage(), e);
        }
        return null;
    }



}
