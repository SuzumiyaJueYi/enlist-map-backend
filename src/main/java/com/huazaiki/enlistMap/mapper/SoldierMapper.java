package com.huazaiki.enlistMap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.vo.ProvinceRecruitVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author huazaiki
* @description 针对表【soldier】的数据库操作Mapper
* @createDate 2024-12-30 18:09:59
* @Entity com.huazaiki.enlistMap.entity.po.Soldier
*/
public interface SoldierMapper extends BaseMapper<Soldier> {

    List<ProvinceRecruitVO> getRecruitCountByProvince(@Param("year") Integer year);

    @MapKey("id")
    List<Map<String, Object>> getYearlyRecruitByProvince(@Param("province") String province);

    @MapKey("id")
    List<Map<String, Object>> getEducationCount(@Param("province") String province, @Param("year") Integer year);

    @MapKey("id")
    List<Map<String, Object>> getNationalRecruitByYear(@Param("year") Integer year);
}




