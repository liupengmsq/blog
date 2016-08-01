package pengliu.me.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.core.Unitils;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByType;
import pengliu.me.dao.CategoryDao;
import pengliu.me.domain.Category;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by peng on 4/10/16.
 */
public class CategoryDaoTest extends BaseDaoTest
{
    private Logger logger = Logger.getLogger(CategoryDaoTest.class);

    @SpringBeanByType
    private CategoryDao categoryDao;

    @Test
    @DataSet("category.cleanup.xml")
    public void testCreateCategory()
    {
        this.logger.info("Start to test create category");
        this.categoryDao.createCategoryByName("my test");
        Assert.assertEquals("my test", this.categoryDao.getAllCategories().get(0).getName());
    }

    @Test
    @DataSet("category.create.xml")
    public void testDeleteCategory()
    {
        this.logger.info("Start to test delete category");
        this.categoryDao.deleteCategoryById(1);
        Assert.assertEquals(0, this.categoryDao.getAllCategories().size());
    }
}
