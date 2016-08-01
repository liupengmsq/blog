package pengliu.me.service;

import pengliu.me.dao.Page;
import pengliu.me.domain.Tag;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.TagVo;

import java.util.List;

/**
 * Created by peng on 16-4-14.
 */
public interface TagService
{
    void createTagByName(String name);
    TagVo findTagById(Integer id);
    List<TagVo> findTagsByIds(Integer... id);
    void updateTagNameById(Integer id, String newName);
    void deleteTagById(Integer id) throws HasBlogRelatedException;
    List<TagVo> getAllTags();
    List<Tag> findTagsPoByIds(Integer... id);
    Page<BlogVo> getAllPagedPublishedBlogsByTagId(Integer id, int pageNo, int pageSize);
}
