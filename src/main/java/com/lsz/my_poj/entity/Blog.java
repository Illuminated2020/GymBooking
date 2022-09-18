package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("blog")
public class Blog {
    private static final long serialVersionUID = 1L;
    //    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //标题
    private Long userId;

    //摘要
    private String description;

    //内容
    private String content;

    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;
}
