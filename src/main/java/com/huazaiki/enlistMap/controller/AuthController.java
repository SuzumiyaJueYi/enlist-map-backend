package com.huazaiki.enlistMap.controller;


import com.huazaiki.enlistMap.common.utils.Result;
import com.huazaiki.enlistMap.entity.dto.UserLoginDTO;
import com.huazaiki.enlistMap.entity.po.User;
import com.huazaiki.enlistMap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huazaiki on 2025/01/06.
 */
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息DTO
     * @return 登陆账户信息
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User user = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            if (user != null) {
                return Result.success(user);
            } else {
                return Result.unauthorized("用户名或密码错误");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
