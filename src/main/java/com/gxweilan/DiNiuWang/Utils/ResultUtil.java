package com.gxweilan.DiNiuWang.Utils;

import com.gxweilan.DiNiuWang.commom.ResultData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultUtil {

    private static final String CODE = "code";
    private static final String MSG = "msg";



    public static <T> ResultData success(Long count, List<T> data){

        return  new ResultData<T>(ResultData.SUCCESS_NUM,"请求成功",count,data);

    }
    public static <T> ResultData failure(){

        return  new ResultData<T>(ResultData.FAILURE_NUM,"请求失败",0L,null);

    }

    public  static Map<String, Object> success(String msg){

        Map<String, Object> retMap = new HashMap<>();
        retMap.put(MSG,msg);
        retMap.put(CODE,ResultData.SUCCESS_NUM);
        return retMap;
    }

    public  static Map<String, Object> failure(String msg){

        Map<String, Object> retMap = new HashMap<>();
        retMap.put(MSG,msg);
        retMap.put(CODE,ResultData.FAILURE_NUM);
        return retMap;
    }
}
