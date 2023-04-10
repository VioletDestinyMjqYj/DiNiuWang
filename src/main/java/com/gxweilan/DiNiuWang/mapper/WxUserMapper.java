package com.gxweilan.DiNiuWang.mapper;

import com.alibaba.fastjson.JSONObject;
import com.gxweilan.DiNiuWang.entity.CompanyTransfer;
import org.apache.ibatis.annotations.Mapper;
import com.gxweilan.DiNiuWang.entity.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 14:15
 */
@Mapper
public interface WxUserMapper {

    public boolean addWxUserInfo(String openid, String md5Openid, JSONObject jsonUserInfo, String avatarUrl, String time);

    public WxUser selWxUserInfoByMd5Openid(String md5Openid);

    public boolean updWxUserInfoByMd5Openid(String md5Openid, JSONObject jsonUserInfo, String avatarUrl, String time);

    WxUser getCompanyPhone(String companyPhone);

    int updWxUserInfoByCpmpanyPhone(WxUser wxUser);

    int insert(WxUser wxUser);

    int deleteById(Integer wuId);

    List<WxUser> seleUserData(@Param("userName")String userName , @Param("myCompany") String myCompany, @Param("taxPaymentType") String taxPaymentType, Integer curPage, Integer pageSize);
}
