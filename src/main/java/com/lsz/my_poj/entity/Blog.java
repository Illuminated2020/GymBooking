package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("blog")
public class Blog {
    private static final long serialVersionUID = 1L;
    //    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //标题
    private String title;

    //摘要
    private String description;

    //内容
    private String content;

}
