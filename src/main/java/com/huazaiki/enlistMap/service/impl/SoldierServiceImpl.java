package com.huazaiki.enlistMap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.service.SoldierService;
import com.huazaiki.enlistMap.mapper.SoldierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* @author huazaiki
* @description 针对表【soldier】的数据库操作Service实现
* @createDate 2024-12-30 18:09:59
*/
@Service
public class SoldierServiceImpl extends ServiceImpl<SoldierMapper, Soldier>
    implements SoldierService{

    @Autowired
    private SoldierMapper soldierMapper;

    /**
     * 是否筛选省份选项
     * @param province
     * @return
     */
    public QueryWrapper<Soldier> isProvinceEmpty(QueryWrapper<Soldier> queryWrapper, String province) {
//        return select ? queryWrapper.eq("province", province) : queryWrapper;
        if (province != null) {
            return queryWrapper.eq("province", province);
        } else {
            return queryWrapper;
        }
    }

    /**
     * 某年根据性别统计数量
     * province为空时，查询某年全国男女性征兵人数
     * @param year
     * @param province
     * @param gender
     * @return
     */
    public Long countSoldierByGender(Integer year, String province, Integer gender) {
        QueryWrapper<Soldier> queryWrapper = new QueryWrapper();
        queryWrapper.eq("enlistment_year", year);
        queryWrapper = isProvinceEmpty(queryWrapper, province);
        queryWrapper.eq("gender", gender);
        return soldierMapper.selectCount(queryWrapper);
    }

    /**
     * 某省某年上半年统计数量
     * province为空时，查询全国某年上半年征兵人数
     * @param year
     * @param province
     * @param gender
     * @return
     */
    public Long countFirstHalfYearSoldier(Integer year, String province) {
        QueryWrapper<Soldier> queryWrapper = new QueryWrapper();
        queryWrapper.eq("enlistment_year", year);
        queryWrapper = isProvinceEmpty(queryWrapper, province);
        queryWrapper.ge("enlistment_month", 1)  // month >= 1
                .le("enlistment_month", 6); // month <= 6
        return soldierMapper.selectCount(queryWrapper);
    }

    /**
     * 某省某年下半年统计数量
     * province为空时，查询全国某年下半年征兵人数
     * @param year
     * @param province
     * @param gender
     * @return
     */
    public Long countLastHalfYearSoldier(Integer year, String province) {
        QueryWrapper<Soldier> queryWrapper = new QueryWrapper();
        queryWrapper.eq("enlistment_year", year);
        queryWrapper = isProvinceEmpty(queryWrapper, province);
        queryWrapper.ge("enlistment_month", 7)  // month >= 1
                .le("enlistment_month", 12); // month <= 6
        return soldierMapper.selectCount(queryWrapper);
    }

    public Long countSoldierByAge(Integer year, String province, Integer age) {
        QueryWrapper<Soldier> queryWrapper = new QueryWrapper();
        queryWrapper.eq("enlistment_year", year);
        queryWrapper = isProvinceEmpty(queryWrapper, province);
        queryWrapper.eq("age", age);
        return soldierMapper.selectCount(queryWrapper);
    }

    @Override
    public Long getSoldierCountByYearAndProvince(Integer year, String province) {
        QueryWrapper<Soldier> queryWrapper = new QueryWrapper();
        queryWrapper.eq("enlistment_year", year);
        if (province != null) {
            queryWrapper.eq("province", province);
        }
        return soldierMapper.selectCount(queryWrapper);
    }

    @Override
    public UserRadarVO getRadarDataByProvince(UserRadarDTO userRadarDTO) {
        UserRadarVO userRadarVO = new UserRadarVO();

        Integer year = userRadarDTO.getYear();
        String province = userRadarDTO.getProvince();

        Long menCount = countSoldierByGender(year, province, 0);
        Long womenCount = countSoldierByGender(year, province, 1);
        Long totalPeople = menCount + womenCount;
        Long firstHalfYearPeople = countFirstHalfYearSoldier(year, province);
        Long lastHalfYearPeople = countLastHalfYearSoldier(year, province);

        List valueList = new ArrayList(Arrays.asList(menCount, womenCount, totalPeople, firstHalfYearPeople, lastHalfYearPeople));
        userRadarVO.setValue(valueList);
        userRadarVO.setName(province + "数据");
        return userRadarVO;
    }

    @Override
    public UserRadarVO getAverageRadarData(UserRadarDTO userRadarDTO) {
        UserRadarVO userRadarVO = new UserRadarVO();

        Integer year = userRadarDTO.getYear();

        Long aveMenCount = countSoldierByGender(year, null, 0) / 34;
        Long aveWomenCount = countSoldierByGender(year, null, 1) / 34;
        Long totalAvePeople = (aveMenCount + aveWomenCount) ;
        Long aveFirstHalfYearPeople = countFirstHalfYearSoldier(year, null) / 34;
        Long aveLastHalfYearPeople = countLastHalfYearSoldier(year, null) / 34;

        List valueList = new ArrayList(Arrays.asList(aveMenCount, aveWomenCount, totalAvePeople, aveFirstHalfYearPeople, aveLastHalfYearPeople));
        userRadarVO.setValue(valueList);
        userRadarVO.setName("全国平均数据");
        return userRadarVO;
    }

    @Override
    public List<Long> getGraphDataByAge(UserRadarDTO userRadarDTO) {
        Integer year = userRadarDTO.getYear();
        String province = userRadarDTO.getProvince();

        List<Long> arrayList = new ArrayList();
        for (int age = 18; age < 23; age++) {
            Long ageCount = countSoldierByAge(year, province, age);
            arrayList.add(ageCount);
        }
        return arrayList;
    }
}




