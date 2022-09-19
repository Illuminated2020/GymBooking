package com.lsz.my_poj.dto;

import com.lsz.my_poj.entity.Blog;
import com.lsz.my_poj.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class BlogDto extends Blog {
    //昵称
    private String nickname;
    //头像
    private String avatarUrl;

    private List<CommentDto> commentDtoList;
}
