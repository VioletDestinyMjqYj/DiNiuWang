package com.gxweilan.DiNiuWang.commom;

import lombok.Data;

import java.util.List;

/**
 * @author 墨清
 */
@Data
public class ResultData<T>{


        //成功编码值
        public static final Integer SUCCESS_NUM = 1;
        //失败编码值
        public static final Integer FAILURE_NUM = 0;

        private Integer code;
        private String msg;
        private Long count;
        private List<T> data;

        public ResultData(Integer code, String msg, Long count, List<T> data){
            this.code = code;
            this.msg = msg;
            this.count = count;
            this.data = data;
        }

    }
