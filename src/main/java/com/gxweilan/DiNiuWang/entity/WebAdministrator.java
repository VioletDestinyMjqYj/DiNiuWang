package com.gxweilan.DiNiuWang.entity;

import lombok.Data;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/9 14:08
 */
@Data
public class WebAdministrator {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 人员编号
     */
    private Long AdminNo;
    /**
     * 管理员账号
     */
    private String account;
    /**
     * 管理员密码
     */
    private String password;
    /**
     * 管理员昵称
     */
    private String nikename;

}
