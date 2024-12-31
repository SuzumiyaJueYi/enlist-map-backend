package com.huazaiki.enlistMap.entity.vo;


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
public class UserSortVO {

    private List<String> nameData;
    private List<Long> countData;
}
