package com.gxweilan.DiNiuWang.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SendMsg {

    @Value("${wxpay.appid}")
    private String appid;

    @Value("${wxpay.secret}")
    private String secret;

    @Value("${wxpay.openid}")
    private String openid;

    @Value("${wxpay.templateId}")
    private String templateId;

    @GetMapping("/sendMsg")
    public void sendMsg(){
        //1:获取token（接口调用凭证）
        String token = queryToken();
        //2:发送订阅消息
        send(token);
    }

    // 1: 获取 access_token  (2h过期)
    public String queryToken(){
        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
        tokenUrl = tokenUrl + "appid=" + appid + "&secret=" + secret;
        String result = HttpUtil.get(tokenUrl);
        System.out.println(tokenUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        System.out.println(result);
        String token = jsonObject.get("access_token").toString();
        System.out.println(token);
        return token;
    }

    public void send(String token){
        String msgUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
        msgUrl = msgUrl + "?access_token=" + token;
        // 设置模板参数
        HashMap<String, Object> paramMap = new HashMap<>();
        System.out.println(openid);
        paramMap.put("touser", openid);                 // 接收方
        paramMap.put("template_id", templateId);        // 模板id
        paramMap.put("page","pages/self/self");         // 消息中要跳转的页面
        // 设置data 模板内容
        HashMap<String, Object> data = new HashMap<>();
        data.put("character_string1.DATA", formatParam("订单编号"));
        data.put("amount2.DATA", formatParam("订单金额"));
        data.put("amount3.DATA", formatParam("本次支付"));
        data.put("thing4.DATA", formatParam("备注"));
        data.put("time5.DATA", formatParam("时间"));
        paramMap.put("data", data);
        System.out.println(data);
        // 转json字符串
        String jsonObject = JSONUtil.toJsonStr(paramMap);
        String result= HttpUtil.post(msgUrl, jsonObject);
        System.out.println(result);
    }

    public HashMap<String, Object> formatParam(String value){
        HashMap<String, Object> data = new HashMap<>();
        data.put("value", value);
        return data;
    }
}
