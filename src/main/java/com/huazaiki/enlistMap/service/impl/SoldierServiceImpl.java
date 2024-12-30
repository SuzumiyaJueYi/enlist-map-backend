package com.huazaiki.enlistMap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.service.SoldierService;
import com.huazaiki.enlistMap.mapper.SoldierMapper;
import org.springframework.stereotype.Service;

/**
* @author huazaiki
* @description 针对表【soldier】的数据库操作Service实现
* @createDate 2024-12-30 18:09:59
*/
@Service
public class SoldierServiceImpl extends ServiceImpl<SoldierMapper, Soldier>
    implements SoldierService{

}




