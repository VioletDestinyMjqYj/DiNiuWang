package com.gxweilan.DiNiuWang.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxweilan.DiNiuWang.Utils.ResultUtil;
import com.gxweilan.DiNiuWang.commom.ResultData;

import com.gxweilan.DiNiuWang.entity.WebAdministrator;
import com.gxweilan.DiNiuWang.serviceAndImpl.AdminService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/9 11:01
 */
@Controller
@RequestMapping("/weblogin")
public class WebLoginController {
    @Resource
    private AdminService adminService;


//    @PostMapping("/login")
//    @ResponseBody
//    public Result login(@Param("account1") String account1, @Param("password1") String password1) {
//        String account = adminService.getAccount();
//        String password = adminService.getPassword();
////        String vCode = String.valueOf(VcodeUtil.getChar());
//        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)){
//            return Result.error(Constants.CODE_400, "参数错误");
//        }
//        System.out.println();
//        WebAdministrator webAdministrator = adminService.login(administrator);
//        return Result.success(webAdministrator);
//    }

    @ResponseBody
    @PostMapping("/adminListData")
    public ResultData adminListData(WebAdministrator webAdministrator, Integer curPage, Integer pageSize){
        //设置分页参数
        PageHelper.startPage(curPage,pageSize);
        //查询数据列表
        List<WebAdministrator> webAdministrators = adminService.getAdminList(webAdministrator);
        //构建分页数据对象
        PageInfo<WebAdministrator> pageInfo = new PageInfo<>(webAdministrators);
        //返回分页数据
        return ResultUtil.success(pageInfo.getTotal(),pageInfo.getList());
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> add(WebAdministrator webAdministrator) {
        if (webAdministrator == null) {

            return ResultUtil.failure("添加失败！");
        }
        WebAdministrator wa = adminService.getAdminByNo(webAdministrator.getAdminNo());
        if(wa != null){
            return ResultUtil.failure("添加失败！该公司编号已经存在！");
        }
        int num = adminService.insert(webAdministrator);
        if (num == 1) {
            return ResultUtil.success("添加成功！");
        }
        return ResultUtil.failure("添加失败！");
    }

    @ResponseBody
    @PostMapping("/edit")
    public Map<String, Object> edit(WebAdministrator webAdministrator) {

        if (webAdministrator == null || webAdministrator.getId() == null) {
            return ResultUtil.failure("编辑失败！要编辑的公司不存在");
        }

        int num = adminService.update(webAdministrator);
        if (num == 1) {
            return ResultUtil.success("编辑成功！");
        }
        return ResultUtil.failure("编辑失败！");
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String, Object> delete(Integer adId) {
        if (adId == null ) {
            return ResultUtil.failure("删除失败！");
        }
        int num = adminService.deleteById(adId);
        if (num == 1) {
            return ResultUtil.success("删除成功！");
        }
        return ResultUtil.failure("删除失败！");
    }

    @ResponseBody
    @PostMapping("batchDelete")
    public Map<String, Object> batchDelete(String adIds) {
        System.out.println(adIds);
        if (StringUtils.isEmpty(adIds)) {
            return ResultUtil.failure("删除失败！");
        }
        int num = adminService.deleteByIds(adIds.split(","));
        if (num >0) {
            return ResultUtil.success("删除成功！");
        }
        return ResultUtil.failure("删除失败！");
    }


}
