package pengliu.me.service;

import pengliu.me.dao.Page;
import pengliu.me.domain.Blog;
import pengliu.me.domain.Category;
import pengliu.me.domain.Tag;
import pengliu.me.exception.BlogNotExistException;
import pengliu.me.exception.UserNotExistException;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;
import pengliu.me.vo.CommentVo;
import pengliu.me.vo.TagVo;

import java.util.List;

/**
 * Created by peng on 16-4-15.
 */
public interface BlogService
{
    void createBlog(BlogVo blogVo, Category category, List<Tag> tags) throws UserNotExistException;
    void updateBlog(BlogVo blogVo, Category category, List<Tag> tags) throws UserNotExistException;
    BlogVo getBlogById(Integer id) throws BlogNotExistException;
    List<CommentVo> getCommentsByBlogId(Integer id) throws BlogNotExistException;
    Page<BlogVo> getAllPagedPublishedBlogs(int pageNo, int pageSize);
    Page<BlogVo> getAllPagedPublishedBlogsByContent(String title, int pageNo, int pageSize);
    List<Blog> getTopTenLatestPublicBLog();
    List<Blog> getTopTenViewCountPublicBLog();
    List<BlogVo> getAllBlogs();
    void deleteBlogById(Integer id);
    void plusBlogViewCount(Integer id);
}
