package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.BlogDao;
import pengliu.me.dao.CommentDao;
import pengliu.me.dao.CommentUserDao;
import pengliu.me.domain.Blog;
import pengliu.me.domain.Comment;
import pengliu.me.domain.CommentUser;
import pengliu.me.exception.BlogNotExistException;
import pengliu.me.exception.CommentUserHasExistException;
import pengliu.me.service.BlogService;
import pengliu.me.service.CommentService;
import pengliu.me.utils.CommonUtil;
import pengliu.me.vo.CommentVo;

/**
 * Created by peng on 6/26/16.
 */
@Service
public class CommentServiceImpl implements CommentService
{
    private Logger logger = Logger.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommentUserDao commentUserDao;

    @Autowired
    private BlogDao blogDao;

    public void createComment(CommentVo commentVo) throws BlogNotExistException, CommentUserHasExistException
    {
        //get exist comment user or create a new one
        CommentUser commentUser = createOrGetCommentUser(commentVo);

        //save comment to db
        Comment comment = new Comment();
        comment.setCreateTime(CommonUtil.getTimeStampNow());
        comment.setContent(commentVo.getContent());
        comment.setCommentUser(commentUser);
        Blog blog = this.blogDao.get(commentVo.getBlogId());
        if(blog == null)
        {
            throw new BlogNotExistException(String.format("Blog doesn't exist for blog id %s!!", commentVo.getBlogId()));
        }
        comment.setBlog(blog);
        this.commentDao.persist(comment);
    }

    public void deleteCommentById(Integer id)
    {
        this.commentDao.delete(id);
    }

    private CommentUser createOrGetCommentUser(CommentVo commentVo) throws CommentUserHasExistException
    {
        //Verify if comment user has been exist
        CommentUser userFindByEmail = this.commentUserDao.findCommentUserByEmail(commentVo.getUserEmail());
        CommentUser userFindByNickName = this.commentUserDao.findCommentUserByNickName(commentVo.getUserName());

        if(userFindByEmail != null && userFindByNickName != null)
        {
            if(userFindByEmail.equals(userFindByNickName))
            {
                return userFindByEmail;
            }
            else
            {
                throw new CommentUserHasExistException(
                        String.format("昵称'%s'和邮箱'%s'已经有人使用了，请换一个吧", commentVo.getUserName(), commentVo.getUserEmail()));
            }
        }

        //save comment user to db
        if(userFindByEmail == null && userFindByNickName == null)
        {
            this.commentUserDao.createCommentUser(commentVo.getUserName(), commentVo.getUserEmail(), commentVo.getUserUrl(), commentVo.getUserremoteIp());
            return this.commentUserDao.findCommentUserByEmail(commentVo.getUserEmail());
        }
        if(userFindByEmail == null)
        {
            throw new CommentUserHasExistException(
                    String.format("昵称'%s'已经有人使用了，请换一个吧", commentVo.getUserName()));
        }
        throw new CommentUserHasExistException(
                String.format("邮箱'%s'已经有人使用了，请换一个吧", commentVo.getUserEmail()));
    }
}
