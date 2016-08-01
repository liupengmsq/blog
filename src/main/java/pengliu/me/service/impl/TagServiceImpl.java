package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.Page;
import pengliu.me.dao.TagDao;
import pengliu.me.domain.Blog;
import pengliu.me.domain.Tag;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.service.TagService;
import pengliu.me.utils.CommonUtil;
import pengliu.me.utils.TransferUtil;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.TagVo;

import java.util.*;

/**
 * Created by peng on 16-4-14.
 */
@Service
public class TagServiceImpl extends BaseService implements TagService
{
    private Logger logger = Logger.getLogger(TagServiceImpl.class);

    @Autowired
    private TagDao tagDao;

    public void createTagByName(String name)
    {
        this.tagDao.createTagByName(name);
    }

    public Tag findTagPoById(Integer id)
    {
        return this.tagDao.get(id);
    }

    public TagVo findTagById(Integer id)
    {
        Tag tag = this.findTagPoById(id);
        TagVo vo = new TagVo();
        vo.setId(tag.getId());
        vo.setName(tag.getName());
        vo.setCreateTime(tag.getCreateTime());
        vo.setUpdateTime(tag.getUpdateTime());

        return vo;
    }

    public void updateTagNameById(Integer id, String newName)
    {
        this.tagDao.updateTagNameById(id, newName);
    }

    public void deleteTagById(Integer id) throws HasBlogRelatedException
    {
        Tag tag = this.tagDao.get(id);
        if(tag == null)
        {
            return;
        }
        if(tag.getBlogs()!=null && tag.getBlogs().size() > 0)
        {
            throw new HasBlogRelatedException(
                    String.format("Tag %s has blog related, can not be deleted!!", id));
        }
        this.tagDao.deleteTagById(id);
    }

    public List<TagVo> getAllTags()
    {
        return TransferUtil.transferTagListPoToVo(this.tagDao.getAllTags());
    }

    public List<TagVo> findTagsByIds(Integer... id)
    {
        return TransferUtil.transferTagListPoToVo(this.findTagsPoByIds());
    }

    public List<Tag> findTagsPoByIds(Integer... id)
    {
        return this.tagDao.getList("id", Arrays.asList(id));
    }

    public Page<BlogVo> getAllPagedPublishedBlogsByTagId(Integer id, int pageNo, int pageSize)
    {
        Tag tag = this.findTagPoById(id);
        List<BlogVo> resultBlogVos = new ArrayList<BlogVo>();
        if(tag != null)
        {
            resultBlogVos =  this.sortAndTransferBlogPoToVoList(tag.getBlogs());
        }

        return CommonUtil.pagedList(resultBlogVos, pageNo, pageSize);
    }
}
