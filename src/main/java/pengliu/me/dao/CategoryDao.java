package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.Category;
import pengliu.me.utils.CommonUtil;

import java.util.List;

/**
 * Created by peng on 4/10/16.
 */
@Repository
public class CategoryDao extends BaseDaoHibernate4<Category>
{
    public void createCategoryByName(String name)
    {
        Category category = new Category();
        category.setName(name);
        category.setCreateTime(CommonUtil.getTimeStampNow());
        category.setUpdateTime(CommonUtil.getTimeStampNow());
        this.persist(category);
    }

    public void updateCategoryNameById(Integer id, String newName)
    {
        Category category = this.get(id);
        category.setName(newName);
        category.setUpdateTime(CommonUtil.getTimeStampNow());
    }

    public void deleteCategoryById(Integer id)
    {
        this.delete(id);
    }

    public List<Category> getAllCategories()
    {
        return this.findAll();
    }
}
