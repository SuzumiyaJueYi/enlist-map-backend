package com.huazaiki.enlistMap.controller;


import com.huazaiki.enlistMap.common.utils.Result;
import com.huazaiki.enlistMap.entity.dto.CommonProvinceDTO;
import com.huazaiki.enlistMap.entity.dto.CommonYearDTO;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.vo.UserPieVO;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.entity.vo.UserSortVO;
import com.huazaiki.enlistMap.service.SoldierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huazaiki on 2024/12/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SoldierService soldierService;

    /**
     * 根据年份给出招收总人数
     */
    @PostMapping("/overview")
    public Result overview(@RequestBody CommonYearDTO commonYearDTO) {
        Integer year = commonYearDTO.getYear();
        try {
            if (year < 2020 || year > 2024) {
                return Result.failure(500, "请输入正确年份");
            }
            Long totalSoldierCount = soldierService.getSoldierCountByYearAndProvince(year, null);
            return Result.success(totalSoldierCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/radar")
    public Result radar(@RequestBody UserRadarDTO userRadarDTO) {
        try {
            if (userRadarDTO == null) {
                return Result.failure(500, "输入条件不合理");
            }
            UserRadarVO radarDataByProvince = soldierService.getRadarDataByProvince(userRadarDTO);
            UserRadarVO averageRadarData = soldierService.getAverageRadarData(userRadarDTO);
            return Result.success(Arrays.asList(radarDataByProvince, averageRadarData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/graph")
    public Result graph(@RequestBody UserRadarDTO userRadarDTO) {
        try {
            if (userRadarDTO == null) {
                return Result.failure(500, "输入条件不合理");
            }
            return Result.success(soldierService.getGraphDataByAge(userRadarDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sort")
    public Result sort(@RequestBody CommonYearDTO commonYearDTO) {
        Integer year = commonYearDTO.getYear();
        try {
            if (year < 2020 || year > 2024) {
                return Result.failure(500, "请输入正确年份");
            }
            UserSortVO recruitCountByProvince = soldierService.getRecruitCountByProvince(year);
            return Result.success(recruitCountByProvince);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/histogram")
    public Result histogram(@RequestBody CommonProvinceDTO commonProvinceDTO) {
        String province = commonProvinceDTO.getProvince();
        try {
            List<Integer> yearlyRecruitByProvince = soldierService.getYearlyRecruitByProvince(province);
            return Result.success(yearlyRecruitByProvince);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/pie")
    public Result pie(@RequestBody UserRadarDTO userRadarDTO) {
        try {
            if (userRadarDTO == null) {
                return Result.failure(500, "输入条件不合理");
            }
            Integer year = userRadarDTO.getYear();
            String province = userRadarDTO.getProvince();
            List<UserPieVO> educationDistribution = soldierService.getEducationDistribution(province, year);
            return Result.success(educationDistribution);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/map")
    public Result map(@RequestBody CommonYearDTO commonYearDTO) {
        Integer year = commonYearDTO.getYear();
        try {
            if (year < 2020 || year > 2024) {
                return Result.failure(500, "请输入正确年份");
            }
            List<UserPieVO> nationalRecruitByYear = soldierService.getNationalRecruitByYear(year);
            return Result.success(nationalRecruitByYear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
