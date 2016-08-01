package pengliu.me.service.impl;

import pengliu.me.domain.Blog;
import pengliu.me.utils.CommonUtil;
import pengliu.me.utils.TransferUtil;
import pengliu.me.vo.BlogVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by peng on 4/20/16.
 */
public abstract class BaseService
{
    public List<BlogVo> sortAndTransferBlogPoToVoList(Set<Blog> blogs)
    {
        List<Blog> blogPoList = new ArrayList<Blog>(blogs);
        List<Blog> publishedBlogPoList = CommonUtil.getPublishedBlogPos(blogPoList);
        List<BlogVo> blogVoList = TransferUtil.transferBlogPosToVos(publishedBlogPoList);
        return CommonUtil.sortBlogVosByCreateTimeDesc(blogVoList);
    }
}
