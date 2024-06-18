package com.predictions.predictions.models.dto;

import lombok.Data;

@Data
public class CommentForm {


    private Long marketId;
    private String content;
    private Long parentId;
    private String createdAt;


}
