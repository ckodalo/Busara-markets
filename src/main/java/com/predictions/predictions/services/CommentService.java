package com.predictions.predictions.services;

import com.predictions.predictions.models.Comment;
import com.predictions.predictions.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    public final CommentRepository commentRepository;

    public CommentService (CommentRepository commentRepository) {

        this.commentRepository = commentRepository;
    }

    public Comment findCommentById (Long commentId) throws Exception {

        Optional<Comment> possibleComment = commentRepository.findById(commentId);

        if (possibleComment.isPresent()) {
            return possibleComment.get();
        }

        else {
            throw(new Exception("failed to fetch comment, no coment with the provided id"));
        }
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

    public List<Comment> getCommentsForMarket(Long marketId) {
        List<Comment> comments = commentRepository.findByMarketIdOrderByCreatedAtAsc(marketId);

//        for (Comment comment : comments) {
//
//            LocalDateTime createdAt = comment.getCreatedAt();
//
//            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
//            LocalDateTime dateTime = LocalDateTime.parse(createdAt.toString(), inputFormatter);
//
//            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            String formattedDate = dateTime.format(outputFormatter);
//
//        }



        return buildNestedComments(comments);
    }

    private List<Comment> buildNestedComments(List<Comment> comments) {
//        Map<Long, Comment> commentMap = comments.stream()
//                .collect(Collectors.toMap(Comment::getId, comment -> comment));

//        Map<Long, Comment> commentMap = new HashMap<>();
//        for (Comment comment : comments) {
//            commentMap.put(comment.getId(), comment);
//        }

        List<Comment> rootComments = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getParent() != null) {
                Comment parent = comment.getParent();
//                Comment parent = commentMap.get(comment.getParent().getId());
                  if (!parent.getReplies().contains(comment)) {
                      parent.getReplies().add(comment);
                  }
            } else {
                rootComments.add(comment);
            }
        }
        return rootComments;
    }

}
