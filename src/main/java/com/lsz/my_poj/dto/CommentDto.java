package com.lsz.my_poj.dto;

import com.lsz.my_poj.entity.Comment;
import lombok.Data;

@Data
public class CommentDto extends Comment {
    //昵称
    private String nickname;
    //头像
    private String avatarUrl;
}
