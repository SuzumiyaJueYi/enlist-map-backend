package com.huazaiki.enlistMap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huazaiki.enlistMap.entity.dto.UserRadarDTO;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.vo.UserPieVO;
import com.huazaiki.enlistMap.entity.vo.UserRadarVO;
import com.huazaiki.enlistMap.entity.vo.UserSortVO;

import java.util.List;

/**
* @author huazaiki
* @description 针对表【soldier】的数据库操作Service
* @createDate 2024-12-30 18:09:59
*/
public interface SoldierService extends IService<Soldier> {

    /**
     * 某省某年征兵人数
     * province为空时，查询某年全国征兵人数
     * @param year
     * @param province
     * @return
     */
    Long getSoldierCountByYearAndProvince(Integer year, String province);

    /**
     *
     * @param userRadarDTO
     * @return
     */
    UserRadarVO getRadarDataByProvince(UserRadarDTO userRadarDTO);

    /**
     *
     * @param userRadarDTO
     * @return
     */
    UserRadarVO getAverageRadarData(UserRadarDTO userRadarDTO);

    /**
     *
     * @param userRadarDTO
     * @return
     */
    List<Long> getGraphDataByAge(UserRadarDTO userRadarDTO);

    UserSortVO getRecruitCountByProvince(Integer year);

    List<Integer> getYearlyRecruitByProvince(String province);

    List<UserPieVO> getEducationDistribution(String province, Integer year);

    List<UserPieVO> getNationalRecruitByYear(Integer year);
}
