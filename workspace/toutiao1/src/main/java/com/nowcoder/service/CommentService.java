package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CommentService {
    @Resource
    private CommentDAO commentDAO;

    public List<Comment> getCommentByEntity(Long entityId, Long entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentDAO.insertComment(comment);
    }

    public int getCommentCount(Long entityId,Long entityType){
        return commentDAO.comentCount(entityId,entityType);
    }
}
