package com.gxweilan.DiNiuWang.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import com.gxweilan.DiNiuWang.Utils.AesCbcUtil;
import com.gxweilan.DiNiuWang.Utils.EncryptUtil;
import com.gxweilan.DiNiuWang.Utils.HttpMethodUtil;
import com.gxweilan.DiNiuWang.serviceAndImpl.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/6 14:14
 */
@RestController
@RequestMapping("/wx")
public class WxLoginController {
    @Autowired
    WxUserService wxUserService;

    @PostMapping("/login")
    public String login(@RequestBody JSONObject req, HttpServletResponse response) {

        //开发标识
        String wxspAppid = "wxf574f969039228e0";
        String wxspSecret = "eaac2f3dae82753885fae7ca3ce763d5";
        String grant_type = "authorization_code";

        //转存微信UserProfile用户信息

        String code = "003cHz000p7IsP1CLv200eOsY02cHz0G";
        String encryptedData = req.getString("encryptedData");
        String iv = req.getString("iv");

        //向腾讯服务器发送请求获取openid
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=authorization_code";
//        System.out.println("1");
        String weChatStr = HttpMethodUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
//        System.out.println("2");
        System.out.println(weChatStr);
        JSONObject jsonWeChatStr = JSONObject.parseObject(weChatStr);
//        System.out.println(jsonWeChatStr);
        String openid = String.valueOf(jsonWeChatStr.get("openid"));
        System.out.println(openid);
        String md5Openid = EncryptUtil.MD5EncryptMethod(openid);
//        System.out.println(md5Openid);
        String session_key = jsonWeChatStr.get("session_key").toString();

        //解密用户信息
        String userInof = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
        System.out.println(userInof);
        JSONObject jsonUserInfo = JSONObject.parseObject(userInof);
        System.out.println(jsonUserInfo);

        //为用户生成token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(md5Openid, "123456");
        try {
            subject.login(token);
            //用户存在，更新个人信息
            response.setHeader("x-auth-token", "");
            response.setHeader("openid", md5Openid);
            wxUserService.updWxUserInfoByMd5Openid(md5Openid, jsonUserInfo);
            return "success";
        } catch (UnknownAccountException aue) {
            //用户不存在，注册新用户
            response.setHeader("x-auth-token", "");
            response.setHeader("openid", md5Openid);
//            System.out.println(EncryptUtil.MD5EncryptMethod(openid));
            wxUserService.addWxUserInfo(openid, md5Openid, jsonUserInfo);
            return "success";
        } catch (AuthenticationException ae) {
            //其他系统错误
            return "failure";
        }
    }

}
