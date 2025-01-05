package com.huazaiki.enlistMap.controller;


import com.huazaiki.enlistMap.common.utils.Result;
import com.huazaiki.enlistMap.entity.dto.CommonProvinceDTO;
import com.huazaiki.enlistMap.entity.dto.CommonYearDTO;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.vo.UserPieVO;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.entity.vo.UserSortVO;
import com.huazaiki.enlistMap.service.SoldierService;
import com.huazaiki.enlistMap.service.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * 数据总览
     * 根据所给年份(year)给出某年招收总人数
     */
    @PostMapping("/overview")
    public Result<Long> overview(@RequestBody CommonYearDTO commonYearDTO) {
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

    /**
     * 雷达图
     * @param userRadarDTO 雷达图DTO
     * @return 某省某年招收数据各项指标
     */
    @PostMapping("/radar")
    public Result<List<UserRadarVO>> radar(@RequestBody UserRadarDTO userRadarDTO) {
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

    /**
     * 折线图
     * @param userRadarDTO 雷达图DTO
     * @return 某省某年根据年龄统计招收人数
     */
    @PostMapping("/graph")
    public Result<List<Long>> graph(@RequestBody UserRadarDTO userRadarDTO) {
        try {
            if (userRadarDTO == null) {
                return Result.failure(500, "输入条件不合理");
            }
            return Result.success(soldierService.getGraphDataByAge(userRadarDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 排序
     * @param commonYearDTO 年份DTO
     * @return 某年招收人数数量按照省份降序
     */
    @PostMapping("/sort")
    public Result<UserSortVO> sort(@RequestBody CommonYearDTO commonYearDTO) {
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

    /**
     * 柱状图
     * @param commonProvinceDTO 年份DTO
     * @return 某省入伍数量年度变化
     */
    @PostMapping("/histogram")
    public Result<List<Integer>> histogram(@RequestBody CommonProvinceDTO commonProvinceDTO) {
        String province = commonProvinceDTO.getProvince();
        try {
            List<Integer> yearlyRecruitByProvince = soldierService.getYearlyRecruitByProvince(province);
            return Result.success(yearlyRecruitByProvince);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 饼图
     * @param userRadarDTO 雷达图DTO
     * @return 某省某年征兵数据学历分布
     */
    @PostMapping("/pie")
    public Result<List<UserPieVO>> pie(@RequestBody UserRadarDTO userRadarDTO) {
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

    /**
     * 大地图
     * @param commonYearDTO 年份DTO
     * @return 某年全国征兵数据
     */
    @PostMapping("/map")
    public Result<List<UserPieVO>> map(@RequestBody CommonYearDTO commonYearDTO) {
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
