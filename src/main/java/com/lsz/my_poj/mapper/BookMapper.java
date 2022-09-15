package com.lsz.my_poj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.my_poj.entity.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
