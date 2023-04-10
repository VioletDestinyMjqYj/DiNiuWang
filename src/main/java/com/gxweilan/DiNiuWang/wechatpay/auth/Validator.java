package com.gxweilan.diniuwang.wechatpay.auth;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * @author xy-peng
 */
public interface Validator {

    boolean validate(CloseableHttpResponse response) throws IOException;

}
