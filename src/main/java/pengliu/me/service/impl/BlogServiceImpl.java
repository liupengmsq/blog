package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.BlogDao;
import pengliu.me.dao.Page;
import pengliu.me.domain.*;
import pengliu.me.exception.BlogNotExistException;
import pengliu.me.exception.UserNotExistException;
import pengliu.me.service.BlogService;
import pengliu.me.service.UserService;
import pengliu.me.utils.CommonUtil;
import pengliu.me.utils.TransferUtil;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CommentVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peng on 16-4-15.
 */
@Service
public class BlogServiceImpl implements BlogService
{
    private Logger logger = Logger.getLogger(BlogServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BlogDao blogDao;

    public void createBlog(BlogVo blogVo, Category category, List<Tag> tags) throws UserNotExistException
    {
        this.saveOrUpdateBlog(blogVo, category, tags, false);
    }

    public void updateBlog(BlogVo blogVo, Category category, List<Tag> tags) throws UserNotExistException
    {
        this.saveOrUpdateBlog(blogVo, category, tags, true);
    }

    private void saveOrUpdateBlog(BlogVo blogVo, Category category, List<Tag> tags, boolean isUpdate) throws UserNotExistException
    {
        this.logger.info("Get admin user");
        User user = this.userService.getAdminUser();
        Blog blog = TransferUtil.transferBlogVoToPo(blogVo);
        blog.setCategory(category);
        blog.setTags(new HashSet<Tag>(tags));
        if(!isUpdate)
        {
            blog.setCreateTime(CommonUtil.getTimeStampNow());
        }
        blog.setUpdateTime(CommonUtil.getTimeStampNow());
        blog.setShowCount(0);
        blog.setUser(user);

        if(isUpdate)
        {
            this.blogDao.update(blog);
        }
        else
        {
            this.blogDao.persist(blog);
        }
    }

    public Page<BlogVo> getAllPagedPublishedBlogs(int pageNo, int pageSize)
    {
        return this.populateAllPagedBlogsPoToVo(this.blogDao.getAllPagedPublishedBlogs(pageNo, pageSize));
    }

    public Page<BlogVo> getAllPagedPublishedBlogsByContent(String content, int pageNo, int pageSize)
    {
        return this.populateAllPagedBlogsPoToVo(this.blogDao.getAllPagedPublishedBlogs(content, pageNo, pageSize));
    }

    public List<Blog> getTopTenLatestPublicBLog()
    {
        return this.blogDao.getTopTenLatestPublicBLog();
    }

    public List<Blog> getTopTenViewCountPublicBLog()
    {
        return this.blogDao.getTopTenViewCountPublicBlog();
    }

    public BlogVo getBlogById(Integer id) throws BlogNotExistException
    {
        Blog blog = this.blogDao.get(id);
        if(blog == null)
        {
            throw new BlogNotExistException("Blog for id " + id + " doesn't exist!!!");
        }

        return TransferUtil.transferBlogPoToVo(blog);
    }

    public List<CommentVo> getCommentsByBlogId(Integer id) throws BlogNotExistException
    {
        Blog blog = this.blogDao.get(id);
        if(blog == null)
        {
            throw new BlogNotExistException("Blog for id " + id + " doesn't exist!!!");
        }

        return TransferUtil.transferBlogPoToCommentVos(blog);
    }

    public List<BlogVo> getAllBlogs()
    {
        return this.populateAllBlogsPoToVo(this.blogDao.getAllBlogs());
    }

    private List<BlogVo> populateAllBlogsPoToVo(List<Blog> blogsFromDB)
    {
        List<BlogVo> blogVos = new ArrayList<BlogVo>();
        for(Blog blog: blogsFromDB)
        {
            blogVos.add(TransferUtil.transferBlogPoToVo(blog));
        }
        return blogVos;
    }

    private Page<BlogVo> populateAllPagedBlogsPoToVo(Page<Blog> poPaged)
    {
        List<BlogVo> blogVos = new ArrayList<BlogVo>();
        for(Blog blog: poPaged.getCurrentPageData())
        {
            blogVos.add(TransferUtil.transferBlogPoToVo(blog));
        }
        Page<BlogVo> pagedVos = new Page<BlogVo>(poPaged.getStartIndex(),
                poPaged.getTotalCount(),
                poPaged.getPageSize(),
                blogVos);
        return pagedVos;
    }

    public void deleteBlogById(Integer id)
    {
        this.blogDao.deleteBlogById(id);
    }

    public void plusBlogViewCount(Integer id)
    {
        Blog blog = this.blogDao.get(id);
        blog.setShowCount(blog.getShowCount() + 1);
    }

}
