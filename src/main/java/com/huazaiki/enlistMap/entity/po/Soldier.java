package com.huazaiki.enlistMap.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName soldier
 */
@TableName(value ="soldier")
@Data
public class Soldier implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String province;

    /**
     * 
     */
    private String education;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private Integer enlistmentYear;

    /**
     * 
     */
    private Integer enlistmentMonth;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}