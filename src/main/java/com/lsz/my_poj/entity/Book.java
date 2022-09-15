package com.lsz.my_poj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book")
public class Book {
    private static final long serialVersionUID = 1L;
    //    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //学生姓名
    private String stuName;

    //专业班级
    private String className;

    //场馆名称
    private String sportsName;

    //日期
    private String dateDay;

    //时间
    private String dateTime;

    //备注
    private String beizhu;

    private Integer totalCount;
}
