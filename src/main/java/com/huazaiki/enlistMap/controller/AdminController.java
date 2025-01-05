package com.huazaiki.enlistMap.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huazaiki.enlistMap.common.utils.Result;
import com.huazaiki.enlistMap.entity.dto.*;
import com.huazaiki.enlistMap.entity.po.Soldier;
import com.huazaiki.enlistMap.entity.po.User;
import com.huazaiki.enlistMap.entity.vo.AdminSoldiersVO;
import com.huazaiki.enlistMap.service.SoldierService;
import com.huazaiki.enlistMap.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by huazaiki on 2024/12/30.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SoldierService soldierService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询所有soldier
     * @param adminQuerySoldierDTO admin接口用来查询soldier信息的实体
     * @return  根据条件分页查询到的soldier数据
     */
    @PatchMapping("/soldier")
    public Result getAllSoldier(@RequestBody AdminPageQueryDTO adminQuerySoldierDTO) {
        Integer pageNum = adminQuerySoldierDTO.getPageNum();
        Integer pageSize = adminQuerySoldierDTO.getPageSize();
        String cacheKey = "soldiers_page_" + pageNum + "_" + pageSize;

        // 尝试从 Redis 获取缓存数据
        Page<Soldier> cachedPage = (Page<Soldier>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedPage != null) {
            return Result.success(new AdminSoldiersVO(cachedPage.getTotal(), cachedPage.getRecords()));
        }

        // 若缓存中没有数据，则查询数据库
        Page<Soldier> page = soldierService.page(new Page<>(pageNum, pageSize));

        // 将查询结果缓存到 Redis
        redisTemplate.opsForValue().set(cacheKey, page, 5, TimeUnit.MINUTES);

        return Result.success(new AdminSoldiersVO(page.getTotal(), page.getRecords()));
    }

    /**
     * 新增soldier
     */
    @PostMapping("/soldier")
    public Result addSoldier(@RequestBody AdminSoldierInfoDTO adminSoldierInfoDTO) {
        try {
            Soldier newSoldier = new Soldier();
            newSoldier.setId(0);
            newSoldier.setName(adminSoldierInfoDTO.getName());
            newSoldier.setGender(adminSoldierInfoDTO.getGender());
            newSoldier.setProvince(adminSoldierInfoDTO.getProvince());
            newSoldier.setEducation(adminSoldierInfoDTO.getEducation());
            newSoldier.setAge(adminSoldierInfoDTO.getAge());
            newSoldier.setEnlistmentYear(adminSoldierInfoDTO.getEnlistment_year());
            newSoldier.setEnlistmentMonth(adminSoldierInfoDTO.getEnlistment_month());
            newSoldier.setCreateTime(new Date());
            newSoldier.setIsDeleted(0);

            boolean is_saved = soldierService.save(newSoldier);
            if (is_saved) {
                return Result.success();
            } else {
                return Result.failure(500, "添加失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改soldier信息
     */
    @PutMapping("/soldier")
    public Result updateSoldier(@RequestBody AdminUpdateSoldierInfoDTO adminUpdateSoldierInfoDTO) {
        try {
            Soldier newSoldier = new Soldier();
            BeanUtils.copyProperties(adminUpdateSoldierInfoDTO, newSoldier);

            boolean is_updated = soldierService.updateById(newSoldier);
            if (is_updated) {
                return Result.success();
            } else {
                return Result.failure(500, "更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id获取一个soldier
     */
    @GetMapping("/soldier")
    public Result getSoldier(@RequestBody CommonIdDTO commonIdDTO) {
        Integer id = commonIdDTO.getId();
        if (id < 0) {
            return Result.failure(500, "请提供合理的id");
        }

        // 尝试从 Redis 获取缓存数据
        String cacheKey = "soldier_" + id;
        Soldier cachedSoldier = (Soldier) redisTemplate.opsForValue().get(cacheKey);
        if (cachedSoldier != null) {
            return Result.success(cachedSoldier);
        }

        // 若缓存中没有数据，则查询数据库
        Soldier soldier = soldierService.getById(id);

        // 将查询结果缓存到 Redis
        if (soldier != null) {
            redisTemplate.opsForValue().set(cacheKey, soldier, 1, TimeUnit.HOURS);
        }

        return Result.success(soldier);
    }

    /**
     * 根据id删除soldier
     */
    @DeleteMapping("/soldier/{id}")
    public Result deleteSoldier(@PathVariable Integer id) {
        try {
            if (id < 0) {
                return Result.failure(500, "请提供合理的id");
            }
            boolean is_deleted = soldierService.removeById(id);
            if (is_deleted) {
                return Result.success();
            } else {
                return Result.failure(500, "删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有user信息
     */
    @GetMapping("/user")
    public Result getAllUsers() {
        // 尝试从 Redis 获取缓存数据
        String cacheKey = "user_list";
        List<User> cachedUsers = (List<User>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedUsers != null) {
            return Result.success(cachedUsers);
        }

        // 若缓存中没有数据，则查询数据库
        List<User> userList = userService.list();

        // 将查询结果缓存到 Redis
        redisTemplate.opsForValue().set(cacheKey, userList, 1, TimeUnit.HOURS);

        return Result.success(userList);
    }


    /**
     * 修改user
     */
    @PostMapping("/user")
    public Result updateUser(@RequestBody AdminUpdateUserInfoDTO adminUpdateUserInfoDTO) {
        try {
            boolean is_updated = userService.updateById(adminUpdateUserInfoDTO);
            if (is_updated) {
                // 更新缓存
                String cacheKey = "user_list";
                redisTemplate.delete(cacheKey);  // 删除用户列表缓存
                return Result.success();
            } else {
                return Result.failure(500, "更新数据失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id获取一个user
     */
    @PutMapping("/user")
    public Result getUserById(@RequestBody CommonIdDTO commonIdDTO) {
        Integer id = commonIdDTO.getId();
        try {
            if (id < 0) {
                return Result.failure(500, "请提供合理的id");
            }
            User user = userService.getById(id);
            return Result.success(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
