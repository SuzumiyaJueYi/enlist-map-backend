package com.huazaiki.enlistMap.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by huazaiki on 2025/01/01.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    private String username;
    private String password;
}
