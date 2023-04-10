package com.gxweilan.diniuwang.wechatpay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 86151
 */
public class ReturnUtil {

    public static JSONObject data(Object o){
        JSONObject jsonTrue = JSON.parseObject("{status: true}");
        jsonTrue.put("msg", o);
        return jsonTrue;
    };

    public static JSONObject fail(Object o){
        JSONObject jsonFail = JSON.parseObject("{status: false}");
        jsonFail.put("msg", o);
        return jsonFail;
    };

    public JSONObject status(boolean status){
        if (status){
            return JSON.parseObject("{status: true}");
        }else {
            return JSON.parseObject("{status: false}");
        }
    };
}
