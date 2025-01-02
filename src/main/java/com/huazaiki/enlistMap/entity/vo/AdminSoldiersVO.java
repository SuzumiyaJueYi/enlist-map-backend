package com.huazaiki.enlistMap.entity.vo;


import com.huazaiki.enlistMap.entity.po.Soldier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huazaiki on 2024/12/31.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSoldiersVO {

    private Long total;
    private List<Soldier> items;
}
