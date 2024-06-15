package com.predictions.predictions.services;

import com.predictions.predictions.models.Comment;
import com.predictions.predictions.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    public final CommentRepository commentRepository;

    public CommentService (CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

    public List<Comment> findAllComments () {

        return commentRepository.findAll();
    }

    public List<Comment> findCommentsByMarket (Long marketId) {

        return commentRepository.findCommentsByMarketId(marketId);
    }


    public Comment addComment (Comment comment) {

       return commentRepository.save(comment);
    }

}
