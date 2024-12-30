package com.huazaiki.enlistMap.controller;


import com.huazaiki.enlistMap.common.utils.Result;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.service.SoldierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

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
     * @param year [2020, 2024]
     * @return
     */
    @PostMapping("/overview")
    public Result overview(@RequestBody Map<String, Integer> yearMap) {
        Integer year = yearMap.get("year");
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
}
