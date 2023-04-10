package com.gxweilan.DiNiuWang.Utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.gxweilan.DiNiuWang.entity.WebAdministrator;
import com.gxweilan.DiNiuWang.serviceAndImpl.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/9 14:07
 */

@Component
public class TokenUtil {

    private static AdminService staticUserService;

    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setUserService() {
        staticUserService = adminService;
    }
    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static WebAdministrator getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getAdminByNo(Long.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
