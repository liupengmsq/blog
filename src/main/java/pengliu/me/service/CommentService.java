package pengliu.me.service;

import pengliu.me.exception.BlogNotExistException;
import pengliu.me.exception.CommentUserHasExistException;
import pengliu.me.vo.CommentVo;

public interface CommentService
{
    void createComment(CommentVo commentVo) throws BlogNotExistException, CommentUserHasExistException;
    void deleteCommentById(Integer id);
}
