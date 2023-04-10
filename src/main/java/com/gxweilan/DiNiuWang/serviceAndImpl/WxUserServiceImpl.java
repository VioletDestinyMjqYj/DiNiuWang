package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.alibaba.fastjson.JSONObject;
import com.gxweilan.DiNiuWang.mapper.WxUserMapper;
import com.gxweilan.DiNiuWang.entity.WxUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 17:13
 */
@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    WxUserMapper wxUserMapper;

    @Override
    public boolean addWxUserInfo(String openid, String md5Openid, JSONObject jsonUserInfo) {
        String avatarUrl = jsonUserInfo.getString("avatarUrl");
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return wxUserMapper.addWxUserInfo(openid, md5Openid, jsonUserInfo, avatarUrl, time);
    }

    @Override
    public WxUser selWxUserInfoByMd5Openid(String md5Openid) {
        return wxUserMapper.selWxUserInfoByMd5Openid(md5Openid);
    }


    @Override
    public boolean updWxUserInfoByMd5Openid(String md5Openid, JSONObject jsonUserInfo) {
        String avatarUrl = jsonUserInfo.getString("avatarUrl");
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        return wxUserMapper.updWxUserInfoByMd5Openid(md5Openid, jsonUserInfo, avatarUrl, time);
    }

    @Override
    public WxUser getCompanyPhone(String companyPhone) {
        if (companyPhone == null){
            return null;
        }
        return wxUserMapper.getCompanyPhone(companyPhone);
    }

    @Override
    public int updWxUserInfoByCpmpanyPhone(WxUser wxUser) {
        if (wxUser == null){
            return 0;
        }
        return wxUserMapper.updWxUserInfoByCpmpanyPhone(wxUser);
    }

    @Override
    public int insert(WxUser wxUser) {
        if (wxUser == null){
            return 0;
        }
        return wxUserMapper.insert(wxUser);
    }

    @Override
    public int deleteById(Integer wuId) {
        if (wuId == null){
            return 0;
        }
        return wxUserMapper.deleteById(wuId);
    }

    @Override
    public void login(String openid, JSONObject jsonUserInfo) {

    }

    @Override
    public List<WxUser> seleUserData(@Param("userName")String userName, @Param("myCompany") String myCompany, @Param("taxPaymentType")String taxPaymentType, Integer curPage, Integer pageSize) {
        return wxUserMapper.seleUserData( userName,  myCompany,  taxPaymentType,  curPage,  pageSize);
    }
}

