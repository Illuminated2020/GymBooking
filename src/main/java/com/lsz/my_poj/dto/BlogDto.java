package com.lsz.my_poj.dto;

import com.lsz.my_poj.entity.Blog;
import lombok.Data;

@Data
public class BlogDto extends Blog {
    //昵称
    private String nickname;
    //头像
    private String avatarUrl;
}
