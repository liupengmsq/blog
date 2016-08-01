package pengliu.me.utils;

import pengliu.me.common.BlogStatus;
import pengliu.me.domain.*;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;
import pengliu.me.vo.CommentVo;
import pengliu.me.vo.TagVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peng on 4/16/16.
 */
public class TransferUtil
{
    public static BlogVo transferBlogPoToVo(Blog blog)
    {
        BlogVo blogVo = new BlogVo();
        blogVo.setId(blog.getId());
        blogVo.setTitle(blog.getTitle());
        blogVo.setSummary(blog.getSummary());
        blogVo.setContent(blog.getContent());
        blogVo.setUpdateTime(blog.getUpdateTime());
        blogVo.setCreateTime(blog.getCreateTime());
        blogVo.setShowCount(blog.getShowCount());
        blogVo.setUserName(blog.getUser().getName());
        blogVo.setStatus(blog.getStatus().toString());
        blogVo.setCategoryVo(
                TransferUtil.transferCategoryPoToVo(blog.getCategory()));
        blogVo.setTagVos(TransferUtil.transferTagSetPoToVo(blog.getTags()));
        blogVo.setFormat(blog.getFormat());
        blogVo.setCommentCount(blog.getComments().size());

        return blogVo;
    }

    public static Blog transferBlogVoToPo(BlogVo blogVo)
    {
        Blog blog = new Blog();
        blog.setId(blogVo.getId());
        blog.setTitle(blogVo.getTitle());
        blog.setSummary(blogVo.getSummary());
        blog.setContent(blogVo.getContent());
        blog.setUpdateTime(blogVo.getUpdateTime());
        blog.setCreateTime(blogVo.getCreateTime());
        blog.setShowCount(blogVo.getShowCount());
        blog.setStatus(BlogStatus.CREATED);
        blog.setFormat(blogVo.getFormat());
        if(blogVo.getStatus() != null)
        {
            blog.setStatus(BlogStatus.valueOf(blogVo.getStatus()));
        }

        return blog;
    }

    public static List<BlogVo> transferBlogPosToVos(List<Blog> blogPos)
    {
        List<BlogVo> blogVos = new ArrayList<BlogVo>();
        for(Blog blog: blogPos)
        {
            blogVos.add(TransferUtil.transferBlogPoToVo(blog));
        }

        return blogVos;
    }

    public static CategoryVo transferCategoryPoToVo(Category category)
    {
        CategoryVo vo = new CategoryVo();
        vo.setId(category.getId());
        vo.setName(category.getName());
        vo.setCreateTime(category.getCreateTime());
        vo.setUpdateTime(category.getUpdateTime());

        return vo;
    }

    public static List<TagVo> transferTagListPoToVo(List<Tag> tags)
    {
        List<TagVo> tagVos = new ArrayList<TagVo>();
        for(Tag tag: tags)
        {
            TagVo tagVo = new TagVo();
            tagVo.setId(tag.getId());
            tagVo.setName(tag.getName());
            tagVo.setChecked(false);
            tagVo.setBlogCount(tag.getBlogs().size());
            tagVo.setCreateTime(tag.getCreateTime());
            tagVo.setUpdateTime(tag.getUpdateTime());

            tagVos.add(tagVo);
        }

        return tagVos;
    }

    public static Set<TagVo> transferTagSetPoToVo(Set<Tag> tags)
    {
        return new HashSet<TagVo>(transferTagListPoToVo(new ArrayList(tags)));
    }

    public static List<CategoryVo> transferCategoryListPoToVo(List<Category> categories)
    {
        List<CategoryVo> categoryVos = new ArrayList<CategoryVo>();
        for(Category cat: categories)
        {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(cat.getId());
            categoryVo.setName(cat.getName());
            categoryVo.setChecked(false);
            categoryVo.setBlogCount(cat.getBlogs().size());
            categoryVo.setCreateTime(cat.getCreateTime());
            categoryVo.setUpdateTime(cat.getUpdateTime());

            categoryVos.add(categoryVo);
        }

        return categoryVos;
    }

    public static List<CommentVo> transferBlogPoToCommentVos(Blog blog)
    {
        List<CommentVo> commentVos = new ArrayList<CommentVo>();
        for (Comment comment: blog.getComments())
        {
            CommentVo commentVo = new CommentVo();
            commentVo.setId(comment.getId());
            commentVo.setContent(comment.getContent());
            commentVo.setCreateTime(comment.getCreateTime());
            CommentUser user = comment.getCommentUser();
            if(user != null)
            {
                commentVo.setUserEmail(user.getEmail());
                commentVo.setUserName(user.getName());
                commentVo.setUserremoteIp(CommonUtil.obscureIPAddress(user.getRemoteIp()));
                commentVo.setUserUrl(user.getBlogUrl());
            }
            commentVos.add(commentVo);
        }

        return commentVos;
    }
}
