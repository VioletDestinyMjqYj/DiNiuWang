package com.gxweilan.DiNiuWang.serviceAndImpl;

import cn.hutool.log.Log;

import com.gxweilan.DiNiuWang.entity.WebAdministrator;
import com.gxweilan.DiNiuWang.mapper.AdminMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/9 14:20
 */
@Service
public class AdminServiceImpl implements AdminService {


    private static final Log LOG = Log.get();
    @Resource
    AdminMapper adminMapper;

    @Override
    public WebAdministrator login(WebAdministrator webAdministrator) {
//        WebAdministrator one = getAdminInfo();
        return null;
    }

    @Override
    public int insert(WebAdministrator webAdministrator) {
        if (webAdministrator == null){
            return 0;
        }
        return adminMapper.insert(webAdministrator);
    }

    @Override
    public int update(WebAdministrator webAdministrator) {
        if (webAdministrator == null || webAdministrator.getAdminNo()==null){
            return 0;
        }
        return adminMapper.update(webAdministrator);
    }

    @Override
    public int deleteById(Integer adId) {
        if (adId == null){
            return 0;
        }
        return adminMapper.deleteById(adId);
    }

    @Override
    public int deleteByIds(String[] adIds) {
        if (adIds == null || adIds.length == 0){
            return 0;
        }
        return adminMapper.deleteByIds(adIds);
    }

    @Override
    public WebAdministrator getAdminByNo(Long adNo) {
        if (adNo == null){
            return null;
        }
        return adminMapper.getAdminByNo(adNo);
    }

    @Override
    public WebAdministrator getAdminByName(String nikename) {
        if (nikename == null){
            return null;
        }
        return adminMapper.getAdminByName(nikename);
    }

    @Override
    public List<WebAdministrator> getAdminList(WebAdministrator webAdministrator) {
        if (webAdministrator == null){
            return null;
        }
        return adminMapper.getAdminList(webAdministrator);
    }

//    private WebAdministrator getUserInfo(WebAdministrator webAdministrator) {
//        QueryWrapper<WebAdministrator> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("account", webAdministrator.getAccount());
//        queryWrapper.eq("password", webAdministrator.getPassword());
//        WebAdministrator one;
//        try {
//            one = getOne(queryWrapper, true); // 从数据库查询用户信息
//        } catch (Exception e) {
//            LOG.error(e);
//            throw new ServiceException(Constants.CODE_500, "系统错误");
//        }
//        return one;
//    }
}
