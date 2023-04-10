package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.gxweilan.DiNiuWang.entity.WebAdministrator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/9 14:19
 */
public interface AdminService {

    WebAdministrator login(WebAdministrator webAdministrator);
    int insert(WebAdministrator webAdministrator);

    int update(WebAdministrator webAdministrator);

    int deleteById(Integer adId);

    int deleteByIds(@Param("adIds") String[] adIds);

    WebAdministrator getAdminByNo (Long adNo);

    WebAdministrator getAdminByName(String nikename);

    List<WebAdministrator> getAdminList(WebAdministrator webAdministrator);
}
