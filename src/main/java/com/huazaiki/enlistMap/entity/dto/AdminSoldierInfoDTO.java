package com.huazaiki.enlistMap.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by huazaiki on 2024/12/31.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSoldierInfoDTO {

    //{
    //  "name": "权线确二",
    //  "gender": 1,
    //  "province": "湖北省",
    //  "education": "本科在读",
    //  "age": 18,
    //  "enlistment_year": 2021,
    //  "enlistment_month": 7
    //}

    private String name;
    private Integer gender;
    private String province;
    private String education;
    private Integer age;
    private Integer enlistment_year;
    private Integer enlistment_month;
}
