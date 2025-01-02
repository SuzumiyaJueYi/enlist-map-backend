package com.huazaiki.enlistMap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huazaiki.enlistMap.entity.po.User;
import com.huazaiki.enlistMap.mapper.UserMapper;
import com.huazaiki.enlistMap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author huazaiki
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-12-30 18:09:59
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        // 查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("is_deleted", 0)); // 确保未被删除
        if (user != null && user.getPassword().equals(password)) {
            return user; // 返回用户信息
        }
        return null; // 返回 null 表示验证失败
    }
}




