package com.letscode.sarafan.service;


import com.letscode.sarafan.domain.Comment;
import com.letscode.sarafan.domain.User;
import com.letscode.sarafan.domain.Views;
import com.letscode.sarafan.dto.EventType;
import com.letscode.sarafan.dto.ObjectType;
import com.letscode.sarafan.repo.CommentRepo;
import com.letscode.sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}