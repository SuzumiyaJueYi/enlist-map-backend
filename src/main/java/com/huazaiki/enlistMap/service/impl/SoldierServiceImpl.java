package com.huazaiki.enlistMap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.vo.ProvinceRecruitVO;
import com.huazaiki.enlistMap.entity.vo.UserPieVO;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.entity.vo.UserSortVO;
import com.huazaiki.enlistMap.mapper.SoldierMapper;
import com.huazaiki.enlistMap.service.SoldierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public UserSortVO getRecruitCountByProvince(Integer year) {
        // 查询结果
        List<ProvinceRecruitVO> recruitList = soldierMapper.getRecruitCountByProvince(year);

        // 转换为所需的数据结构
        UserSortVO userSortVO = new UserSortVO();
        userSortVO.setNameData(recruitList.stream()
                .map(ProvinceRecruitVO::getProvince)
                .toList());
        userSortVO.setCountData(recruitList.stream()
                .map(ProvinceRecruitVO::getRecruitCount)
                .toList());
        return userSortVO;
    }

    public List<Integer> getYearlyRecruitByProvince(String province) {
        // 查询结果
        List<Map<String, Object>> queryResult = soldierMapper.getYearlyRecruitByProvince(province);

        // 准备数据数组
        int startYear = 2020; // 开始年份
        int endYear = 2024;   // 结束年份（可根据实际需要调整）
        int years = endYear - startYear + 1;
        List<Integer> recruitData = new ArrayList<>(Collections.nCopies(years, 0));

        // 填充数据
        for (Map<String, Object> entry : queryResult) {
            int year = (Integer) entry.get("year");
            int recruitCount = ((Long) entry.get("recruit_count")).intValue();
            recruitData.set(year - startYear, recruitCount);
        }

        return recruitData;
    }

    @Override
    public List<UserPieVO> getEducationDistribution(String province, Integer year) {
        List<Map<String, Object>> queryResult = soldierMapper.getEducationCount(province, year);

        return queryResult.stream()
                .map(entry -> {
                    UserPieVO vo = new UserPieVO();
                    vo.setName((String) entry.get("name"));
                    vo.setValue((Long) entry.get("value"));
                    return vo;
                })
                .toList();
    }

    @Override
    public List<UserPieVO> getNationalRecruitByYear(Integer year) {
        // 调用 Mapper 查询结果
        List<Map<String, Object>> queryResult = soldierMapper.getNationalRecruitByYear(year);

        // 封装数据到 UserPieVO
        return queryResult.stream()
                .map(entry -> new UserPieVO(
                        ((Long) entry.get("value")), // 转换 value 为 Long
                        (String) entry.get("name")   // 获取省份名称
                ))
                .toList();
    }
}




