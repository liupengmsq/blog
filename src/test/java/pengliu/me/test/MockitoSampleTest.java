package pengliu.me.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import pengliu.me.service.CategoryService;
import pengliu.me.service.impl.CategoryServiceImpl;
import pengliu.me.utils.CommonUtil;
import pengliu.me.vo.CategoryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 16-4-11.
 */
public class MockitoSampleTest
{
    // 创建加mock的CategoryService接口
    CategoryService mockCategoryService = mock(CategoryService.class);

    // 创建加mock的categoryServiceImpl实现类
    CategoryServiceImpl mockCategoryServiceImpl = mock(CategoryServiceImpl.class);

    @Before
    public void initMocks()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockInterface()
    {
        CategoryVo category = new CategoryVo();
        category.setName("my new category");
        category.setCreateTime(CommonUtil.getTimeStampNow());
        category.setUpdateTime(CommonUtil.getTimeStampNow());
        List<CategoryVo> mockResult = new ArrayList<CategoryVo>();
        mockResult.add(category);

        //模拟getAllCategories接口，使其返回上面构造的category对象
        when(this.mockCategoryService.getAllCategories()).thenReturn(mockResult);

        List<CategoryVo> myMockResult = this.mockCategoryService.getAllCategories();
        assertEquals(1, mockResult.size());
        assertEquals(category, mockResult.get(0));

        // 判断getAllCategories接口方法至少被调用了一次
        verify(mockCategoryService, atLeastOnce()).getAllCategories();
    }
}
