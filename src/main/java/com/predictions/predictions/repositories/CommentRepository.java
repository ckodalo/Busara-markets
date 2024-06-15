package com.predictions.predictions.repositories;

import com.predictions.predictions.models.Comment;
import com.predictions.predictions.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findCommentsByMarketId (Long marketId);

}
