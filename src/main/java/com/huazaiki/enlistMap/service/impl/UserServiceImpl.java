package com.huazaiki.enlistMap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huazaiki.enlistMap.entity.po.User;
import com.huazaiki.enlistMap.service.UserService;
import com.huazaiki.enlistMap.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author huazaiki
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-12-30 18:09:59
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




