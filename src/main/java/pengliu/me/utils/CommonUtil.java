package pengliu.me.utils;

import org.springframework.util.Assert;
import pengliu.me.common.BlogStatus;
import pengliu.me.dao.Page;
import pengliu.me.domain.Blog;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CommentVo;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by peng on 4/10/16.
 */
public class CommonUtil
{
    public static Timestamp getTimeStampNow()
    {
       return new Timestamp(new Date().getTime());
    }

    public static <T> Page<T> pagedList(List<T> unpagedObjects, int pageNo, int pageSize)
    {
        Assert.notNull(unpagedObjects);
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

        if (unpagedObjects.size() < 1)
            return new Page<T>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartIndexOfPage(pageNo, pageSize);
        int endIndex = (startIndex + pageSize) > unpagedObjects.size() ?  unpagedObjects.size() : (startIndex + pageSize);
        List<T> pageList = unpagedObjects.subList(startIndex, endIndex);

        return new Page<T>(startIndex, unpagedObjects.size(), pageSize, pageList);
    }

    public static List<Blog> getPublishedBlogPos(List<Blog> blogs)
    {
        List<Blog> filteredBlog = new ArrayList<Blog>();
        for (Blog blog : blogs)
        {
            if (blog.getStatus() == BlogStatus.PUBLISHED)
            {
                filteredBlog.add(blog);
            }
        }
        return filteredBlog;
    }

    public static List<BlogVo> sortBlogVosByCreateTimeDesc(List<BlogVo> blogVos)
    {
        Collections.sort(blogVos, new Comparator<BlogVo>()
        {
            public int compare(BlogVo o1, BlogVo o2)
            {
                int ret = o1.getCreateTime().compareTo(o2.getCreateTime());
                if(ret > 0)
                {
                    return -1;
                }
                if(ret < 0)
                {
                    return 1;
                }
                return ret;
            }
        });
        return blogVos;
    }

    public static List<CommentVo> sortCommentVosByCreateTimeDesc(List<CommentVo> commentVos)
    {
        Collections.sort(commentVos, new Comparator<CommentVo>()
        {
            public int compare(CommentVo o1, CommentVo o2)
            {
                int ret = o1.getCreateTime().compareTo(o2.getCreateTime());
                if(ret > 0)
                {
                    return -1;
                }
                if(ret < 0)
                {
                    return 1;
                }
                return ret;
            }
        });
        return commentVos;
    }

    public static String obscureIPAddress(String ipAddress)
    {
        String[] ipArray = ipAddress.split("\\.");
        if(ipArray.length == 4)
        {
            return ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + ".*";
        }
        return ipAddress;
    }
}
