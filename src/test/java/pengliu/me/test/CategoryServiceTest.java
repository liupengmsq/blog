package pengliu.me.test;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByType;
import pengliu.me.service.CategoryService;

/**
 * Created by peng on 16-4-11.
 */
public class CategoryServiceTest extends BaseServiceTest
{
    @SpringBeanByType
    private CategoryService categoryService;

    @Test
    public void testApplicationContext()
    {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testCategoryService()
    {
        this.categoryService.createCategoryByName("test 123");
    }
}
