package com.huazaiki.enlistMap.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huazaiki on 2024/12/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRadarVO {

    private List<Object> value;
    private String name;
}
