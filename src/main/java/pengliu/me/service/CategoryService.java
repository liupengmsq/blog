package pengliu.me.service;

import pengliu.me.dao.Page;
import pengliu.me.domain.Category;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;

import java.util.List;

/**
 * Created by peng on 4/11/16.
 */
public interface CategoryService
{
    void createCategoryByName(String name);
    CategoryVo findCategoryById(Integer id);
    Category findCategoryPoById(Integer id);
    void updateCategoryNameById(Integer id, String newName);
    void deleteCategoryById(Integer id) throws HasBlogRelatedException;
    List<CategoryVo> getAllCategories();
    Page<BlogVo> getAllPagedPublishedBlogsByCategoryId(Integer id, int pageNo, int pageSize);
}
