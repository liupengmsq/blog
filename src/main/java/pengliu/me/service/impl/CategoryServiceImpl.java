package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.CategoryDao;
import pengliu.me.dao.Page;
import pengliu.me.domain.Blog;
import pengliu.me.domain.Category;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.service.CategoryService;
import pengliu.me.utils.CommonUtil;
import pengliu.me.utils.TransferUtil;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 4/11/16.
 */
@Service
public class CategoryServiceImpl extends BaseService implements CategoryService
{
    private Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    public void createCategoryByName(String name)
    {
        this.categoryDao.createCategoryByName(name);
    }

    public CategoryVo findCategoryById(Integer id)
    {
        Category category = this.findCategoryPoById(id);

        CategoryVo vo = new CategoryVo();
        vo.setId(category.getId());
        vo.setName(category.getName());
        vo.setCreateTime(category.getCreateTime());
        vo.setUpdateTime(category.getUpdateTime());

        return vo;
    }

    public Category findCategoryPoById(Integer id)
    {
        return this.categoryDao.get(id);
    }

    public Page<BlogVo> getAllPagedPublishedBlogsByCategoryId(Integer id, int pageNo, int pageSize)
    {
        Category category = this.findCategoryPoById(id);
        List<BlogVo> resultBlogVos = new ArrayList<BlogVo>();
        if(category != null)
        {
            resultBlogVos = this.sortAndTransferBlogPoToVoList(category.getBlogs());
        }
        return CommonUtil.pagedList(resultBlogVos, pageNo, pageSize);
    }

    public void updateCategoryNameById(Integer id, String newName)
    {
        this.categoryDao.updateCategoryNameById(id, newName);
    }

    public void deleteCategoryById(Integer id) throws HasBlogRelatedException
    {
        Category category = this.categoryDao.get(id);
        if(category == null)
        {
            return;
        }
        if(category.getBlogs() != null && category.getBlogs().size() > 0)
        {
            throw new HasBlogRelatedException(
                    String.format("Category %s has blog related, can not be deleted!!", id));
        }
        this.categoryDao.deleteCategoryById(id);
    }

    public List<CategoryVo> getAllCategories()
    {
        return TransferUtil.transferCategoryListPoToVo(this.categoryDao.getAllCategories());
    }
}
