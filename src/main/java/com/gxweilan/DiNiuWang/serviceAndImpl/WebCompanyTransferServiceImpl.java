package com.gxweilan.DiNiuWang.serviceAndImpl;

import com.gxweilan.DiNiuWang.entity.CompanyTransfer;
import com.gxweilan.DiNiuWang.mapper.WebCompanyTransferMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 16:36
 */
@Service
public class WebCompanyTransferServiceImpl implements WebCompanyTransferService {
    @Resource
    private WebCompanyTransferMapper webCompanyTransferMapper;


    @Override
    public int insert(CompanyTransfer companyTransfer) {
        if (companyTransfer == null){
            return 0;
        }
        return webCompanyTransferMapper.insert(companyTransfer);
    }

    @Override
    public int update(CompanyTransfer companyTransfer) {
        if (companyTransfer == null || companyTransfer.getId() == null){
            return 0;
        }
        return webCompanyTransferMapper.update(companyTransfer);
    }

    @Override
    public int deleteById(Integer ctId) {
        if (ctId == null){
            return 0;
        }
        return webCompanyTransferMapper.deleteById(ctId);
    }

    @Override
    public int deleteByIds(String[] ctIds) {
        if (ctIds == null || ctIds.length == 0){
            return 0;
        }
        return webCompanyTransferMapper.deleteByIds(ctIds);
    }

    @Override
    public CompanyTransfer getCompanyById(Integer ctId) {
        if (ctId == null){
            return null;
        }
        return webCompanyTransferMapper.getCompanyById(ctId);
    }

    @Override
    public CompanyTransfer getCompanyByName(String companyName) {
        if (companyName == null){
            return null;
        }
        return webCompanyTransferMapper.getCompanyByName(companyName);
    }


    @Override
    public List<CompanyTransfer> getCompanyListByR(CompanyTransfer companyTransfer) {
        return webCompanyTransferMapper.getCompanyListByR(companyTransfer);
    }

    @Override
    public List<CompanyTransfer> getCompanyListByRight(Integer curPage, Integer pageSize) {
            return webCompanyTransferMapper.getCompanyListByRight(curPage,pageSize);
    }

    @Override
    public List<CompanyTransfer> getCompanyList(@Param("companyCode")
    String companyCode, @Param("companyName") String companyName, @Param("taxPaymentType") String taxPaymentType,Integer curPage, Integer pageSize) {
        return webCompanyTransferMapper.getCompanyList(companyCode,companyName,taxPaymentType,curPage,pageSize);
    }


}
