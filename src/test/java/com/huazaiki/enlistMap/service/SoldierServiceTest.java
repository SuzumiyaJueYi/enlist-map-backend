package com.huazaiki.enlistMap.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.enums.ProvinceEnum;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.mapper.SoldierMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by huazaiki on 2024/12/30.
 */
@SpringBootTest
public class SoldierServiceTest {

    @Autowired
    private SoldierService soldierService;

    @Autowired
    private SoldierMapper soldierMapper;

    @Test
    public void testCount() {
        UserRadarDTO userRadarDTO = new UserRadarDTO();
        userRadarDTO.setYear(2020);
        userRadarDTO.setProvince(ProvinceEnum.BEIJING.getChineseName());
        System.out.println(soldierService.getRadarDataByProvince(userRadarDTO));
    }

    @Test
    public void testCount02() {
        UserRadarDTO userRadarDTO = new UserRadarDTO();
        userRadarDTO.setYear(2020);
        System.out.println(soldierService.getAverageRadarData(userRadarDTO));
    }
}
