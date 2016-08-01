package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.common.BlogStatus;
import pengliu.me.domain.Blog;

import java.util.List;

/**
 * Created by peng on 16-4-15.
 */
@Repository
public class BlogDao extends BaseDaoHibernate4<Blog>
{
    public List<Blog> getAllBlogs()
    {
        return this.findAll();
    }

    private Page<Blog> getAllPagedBlogsByStatus(BlogStatus status, String searchColumn, String searchValue, int pageNo, int pageSize)
    {
        if(searchValue == null)
        {
            return this.getPagedOrderedList("status", status, "createTime", true, pageNo, pageSize);
        }
        else
        {
            return this.getPagedOrderedList("status", status, searchColumn, searchValue, "createTime", true, pageNo, pageSize);
        }
    }

    public List<Blog> getTopTenLatestPublicBLog()
    {
        return getTopTenPublicBlogByColumn("createTime");
    }

    public List<Blog> getTopTenViewCountPublicBlog()
    {
        return getTopTenPublicBlogByColumn("showCount");
    }

    private List<Blog> getTopTenPublicBlogByColumn(String  columName)
    {
        return (List<Blog>)this.getSessionFactory().getCurrentSession()
                .createQuery("select en from " + this.getEntityClass().getSimpleName()+ " en order by en."+columName+" desc")
                .setMaxResults(10)
                .list();
    }


    public Page<Blog> getAllPagedPublishedBlogs(int pageNo, int pageSize)
    {
        return this.getAllPagedBlogsByStatus(BlogStatus.PUBLISHED, null, null, pageNo, pageSize);
    }

    public Page<Blog> getAllPagedPublishedBlogs(String searchValue, int pageNo, int pageSize)
    {
        return this.getAllPagedBlogsByStatus(BlogStatus.PUBLISHED, "content", searchValue, pageNo, pageSize);
    }

    public void deleteBlogById(Integer id)
    {
        Blog blog = this.get(id);
        if(blog != null)
        {
            this.delete(blog);
        }
    }
}
