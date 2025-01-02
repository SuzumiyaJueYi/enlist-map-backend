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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @PatchMapping("/soldier")
    public Result getAllSoldier(@RequestBody AdminPageQueryDTO adminQuerySoldierDTO) {
        Integer pageNum = adminQuerySoldierDTO.getPageNum();
        Integer pageSize = adminQuerySoldierDTO.getPageSize();
        try {
//            PageInfo<Soldier> soldierByPage = soldierService.getSoldierByPage(pageNum, pageSize);
            Page<Soldier> page = soldierService.page(new Page<>(pageNum, pageSize));
            return Result.success(new AdminSoldiersVO(page.getTotal(), page.getRecords()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    @GetMapping("/soldier")
    public Result getSoldier(@RequestBody CommonIdDTO commonIdDTO) {
        Integer id = commonIdDTO.getId();
        try {
            if (id < 0) {
                return Result.failure(500, "请提供合理的id");
            }
            Soldier soldier = soldierService.getById(id);
            return Result.success(soldier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    @GetMapping("/user")
    public Result getAllUsers() {
        try {
            List<User> userList = userService.list();
            return Result.success(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/user")
    public Result updateUser(@RequestBody AdminUpdateUserInfoDTO adminUpdateUserInfoDTO) {
        try {
            if (adminUpdateUserInfoDTO == null) {
                return Result.failure(500, "更新数据为空");
            }
            boolean is_updated = userService.updateById(adminUpdateUserInfoDTO);
            if (is_updated) {
                return Result.success();
            } else {
                return Result.failure(500, "更新数据失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
