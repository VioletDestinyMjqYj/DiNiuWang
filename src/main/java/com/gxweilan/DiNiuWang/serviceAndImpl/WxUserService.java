package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.alibaba.fastjson.JSONObject;
import com.gxweilan.DiNiuWang.entity.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 17:12
 */
public interface WxUserService {
    public boolean addWxUserInfo(String openid, String md5Openid, JSONObject jsonUserInfo);

    public WxUser selWxUserInfoByMd5Openid(String md5Openid);

    public boolean updWxUserInfoByMd5Openid(String md5Openid, JSONObject jsonUserInfo);

    WxUser getCompanyPhone(String companyPhone);

    int updWxUserInfoByCpmpanyPhone(WxUser wxUser);

    int insert(WxUser wxUser);

    int deleteById(Integer wuId);

    void login(String openid, JSONObject jsonUserInfo);

    List<WxUser> seleUserData(@Param("userName")String userName , @Param("myCompany") String myCompany, @Param("taxPaymentType") String taxPaymentType, Integer curPage, Integer pageSize);

}
