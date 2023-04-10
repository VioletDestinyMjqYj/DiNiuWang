package com.gxweilan.DiNiuWang.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxweilan.DiNiuWang.Utils.ResultUtil;
import com.gxweilan.DiNiuWang.commom.ResultData;
import com.gxweilan.DiNiuWang.entity.CompanyTransfer;
import com.gxweilan.DiNiuWang.serviceAndImpl.WebCompanyTransferService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 16:11
 */
@Controller
@RequestMapping("/dnw")
public class WebCompanyTransferController {

    @Resource
    WebCompanyTransferService webCompanyTransferService;

    @ResponseBody
    @PostMapping("/transferCompany")
    public ResultData transferCompany (Integer curPage, Integer pageSize){
        //设置分页参数
        System.out.println("1");
//        PageHelper.startPage(curPage,pageSize);
        //查询数据列表
        System.out.println("2");
        List<CompanyTransfer> companyTransfers = webCompanyTransferService.getCompanyListByRight(curPage,pageSize);
        //构建分页数据对象
        System.out.println("3");
        PageInfo<CompanyTransfer> pageInfo = new PageInfo<>(companyTransfers);
        System.out.println("4");
        //返回分页数据
        return ResultUtil.success(pageInfo.getTotal(), pageInfo.getList());
    }

    @ResponseBody
    @PostMapping("/companyListData")
    public ResultData companyListData(@Param("companyCode") String companyCode,@Param("companyName")
    String companyName, @Param("taxPaymentType") String taxPaymentType,Integer curPage, Integer pageSize){
        //设置分页参数
        PageHelper.startPage(curPage,pageSize);
        //查询数据列表
        List<CompanyTransfer> companyTransfers = webCompanyTransferService.getCompanyList(companyCode,companyName,taxPaymentType,curPage,pageSize);
        //构建分页数据对象
        PageInfo<CompanyTransfer> pageInfo = new PageInfo<>(companyTransfers);
        //返回分页数据
        return ResultUtil.success(pageInfo.getTotal(), pageInfo.getList());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(CompanyTransfer companyTransfer) {
        if (companyTransfer == null) {
            return ResultUtil.failure("添加失败！");
        }
        CompanyTransfer cr = webCompanyTransferService.getCompanyById(companyTransfer.getId());
        if(cr != null){
            return ResultUtil.failure("添加失败！该公司编号已经存在！");
        }
        int num = webCompanyTransferService.insert(companyTransfer);
        if (num == 1) {
            return ResultUtil.success("添加成功！");
        }
        return ResultUtil.failure("添加失败！");
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Map<String, Object> edit(CompanyTransfer companyTransfer) {
        if (companyTransfer == null || companyTransfer.getId() == null) {
            return ResultUtil.failure("编辑失败！要编辑的公司不存在");
        }
        int num = webCompanyTransferService.update(companyTransfer);
        if (num == 1) {
            return ResultUtil.success("编辑成功！");
        }
        return ResultUtil.failure("编辑失败！");
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer ctId) {
        if (ctId == null ) {
            return ResultUtil.failure("删除失败！");
        }
        int num = webCompanyTransferService.deleteById(ctId);
        if (num == 1) {
            return ResultUtil.success("删除成功！");
        }
        return ResultUtil.failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping("batchDelete")
    public Map<String, Object> batchDelete(String ctIds) {
        if (StringUtils.isEmpty(ctIds)) {
            return ResultUtil.failure("删除失败！");
        }
        int num = webCompanyTransferService.deleteByIds(ctIds.split(","));
        if (num >0) {
            return ResultUtil.success("删除成功！");
        }
        return ResultUtil.failure("删除失败！");
    }

}
