package com.predictions.predictions.controllers;

import com.predictions.predictions.models.Comment;
import com.predictions.predictions.models.Market;
import com.predictions.predictions.models.dto.CommentForm;
import com.predictions.predictions.services.CommentService;
import com.predictions.predictions.services.MarketService;
import com.predictions.predictions.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private  final UserService userService;

    private final MarketService marketService;

    public CommentController (CommentService commentService, UserService userService, MarketService marketService) {

        this.commentService = commentService;

        this.userService = userService;

        this.marketService = marketService;
    }

//    public String getComments () {
//
//        commentService.findAllComments();
//    }

    @PostMapping("/create")
    public String addComment(@AuthenticationPrincipal User userDetails, @ModelAttribute CommentForm commentForm) throws Exception {

        String username = userDetails.getUsername();

        Market market = marketService.findMarketById(commentForm.getMarketId());

        Comment newComment = new Comment();

        if (commentForm.getParentId() != null) {

            Comment parent = commentService.findCommentById(commentForm.getParentId());
            newComment.setParent(parent);
        }

        com.predictions.predictions.models.User user = userService.findByUsername(username);

        newComment.setAuthor(user);

        newComment.setContent(commentForm.getContent());

        newComment.setMarket(market);

        commentService.addComment(newComment);

        return "redirect:/markets";
    }

}
