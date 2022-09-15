package com.lsz.my_poj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsz.my_poj.entity.Book;
import com.lsz.my_poj.mapper.BookMapper;
import com.lsz.my_poj.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp extends ServiceImpl<BookMapper, Book> implements BookService {
}
