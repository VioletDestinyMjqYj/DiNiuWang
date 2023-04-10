package com.gxweilan.DiNiuWang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxweilan.DiNiuWang.Utils.ResultUtil;
import com.gxweilan.DiNiuWang.commom.ResultData;
import com.gxweilan.DiNiuWang.entity.CompanyTransfer;
import com.gxweilan.DiNiuWang.entity.WxUser;
import com.gxweilan.DiNiuWang.mapper.WxUserMapper;
import com.gxweilan.DiNiuWang.serviceAndImpl.WxUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/8 10:50
 */
@Controller
@RequestMapping("/user")
public class WxUserController {

    @Resource
    private WxUserService wxUserService;

    @ResponseBody
    @PostMapping("/seleUserData")
    public ResultData seleUserData(@Param("userName")String userName , @Param("myCompany") String myCompany, @Param("taxPaymentType")
    String taxPaymentType, Integer curPage, Integer pageSize){
        //设置分页参数
        PageHelper.startPage(curPage,pageSize);
        //查询数据列表
        List<WxUser> wxUsers = wxUserService.seleUserData(userName,myCompany,taxPaymentType,curPage,pageSize);
//        System.out.println(id);
        //构建分页数据对象
        PageInfo<WxUser> pageInfo = new PageInfo<>(wxUsers);
        //返回分页数据
        return ResultUtil.success(pageInfo.getTotal(), pageInfo.getList());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(WxUser wxUser) {
        if (wxUser == null) {
            return ResultUtil.failure("添加失败！请注意输入的字段不能为空！");
        }
        WxUser wu = wxUserService.getCompanyPhone(wxUser.getCompanyPhone());
        if(wu != null){
            return ResultUtil.failure("添加失败！该用户已经存在！");
        }
        int num = wxUserService.insert(wxUser);
        if (num == 1) {
            return ResultUtil.success("添加成功！");
        }
        return ResultUtil.failure("添加失败！");
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Map<String, Object> edit(WxUser wxUser) {
        if (wxUser == null || wxUser.getId() == null) {
            return ResultUtil.failure("编辑失败！要编辑的用户不存在");
        }
        int num = wxUserService.updWxUserInfoByCpmpanyPhone(wxUser);
        if (num == 1) {
            return ResultUtil.success("编辑成功！");
        }
        return ResultUtil.failure("编辑失败！");
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer wuId) {
        if (wuId == null ) {
            return ResultUtil.failure("删除失败！");
        }
        int num = wxUserService.deleteById(wuId);
        if (num == 1) {
            return ResultUtil.success("删除成功！");
        }
        return ResultUtil.failure("删除失败！");
    }
}
