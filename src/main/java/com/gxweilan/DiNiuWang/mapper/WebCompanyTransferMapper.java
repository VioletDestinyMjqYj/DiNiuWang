package com.gxweilan.DiNiuWang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.gxweilan.DiNiuWang.entity.CompanyTransfer;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author 墨杨清竹
 * @version 1.0
 * @date 2023/2/7 16:12
 */
@Mapper
public interface WebCompanyTransferMapper {

    int insert(CompanyTransfer companyTransfer);

    int update(CompanyTransfer companyTransfer);

    int deleteById(Integer ctId);

    int deleteByIds(@Param("ctIds") String[] ctIds);

    CompanyTransfer getCompanyById (Integer ctId);

    CompanyTransfer getCompanyByName(String companyName);

    List<CompanyTransfer> getCompanyListByR(CompanyTransfer companyTransfer);

    List<CompanyTransfer> getCompanyListByRight(Integer curPage, Integer pageSize);

    List<CompanyTransfer> getCompanyList(@Param("companyCode") String companyCode,@Param("companyName") String companyName,@Param("taxPaymentType") String taxPaymentType,Integer curPage,Integer pageSize);


}
