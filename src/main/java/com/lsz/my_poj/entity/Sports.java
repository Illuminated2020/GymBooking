package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sports")
public class Sports {
    private static final long serialVersionUID = 1L;
    //    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //场馆名称
    private String name;

    //预约次数
    private Integer count;

    //场馆照片
    private String image;

    //描述信息
    private String description;

    //状态
    private Integer status;
}
