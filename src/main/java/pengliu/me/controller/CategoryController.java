package pengliu.me.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.common.CommonConstant;
import pengliu.me.dao.Page;
import pengliu.me.domain.Category;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.service.CategoryService;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class CategoryController extends BaseController
{
    private Logger logger = Logger.getLogger(CategoryController.class);

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public ModelAndView getBlogsByCategory(@PathVariable Integer categoryId,
                                           @RequestParam(value = "pageNo", required = false) Integer pageNo)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(pageNo == null)
        {
            pageNo = 1;
        }

        CategoryVo categoryVo = this.getCategoryService().findCategoryById(categoryId);
        modelAndView.addObject("category", categoryVo);

        Page<BlogVo> blogVos = this.getCategoryService().getAllPagedPublishedBlogsByCategoryId(categoryId, pageNo, CommonConstant.PAGE_SIZE);
        modelAndView.addObject("pageResult", blogVos);
        addAllTagAndCategoriesToModelAndView(modelAndView);
        addTopTenBlogToModelAndView(modelAndView);
        addFriendlyLinksToModelAndView(modelAndView);
        addBookRecommendationsToModelAndView(modelAndView);
        addPostRecommendationsToModelAndView(modelAndView);
        modelAndView.setViewName("/main");

        return modelAndView;
    }

    @RequestMapping(value = "/management/category/listAll", method = RequestMethod.GET)
    public ModelAndView listAllCategories(@RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryVo> categories = this.getCategoryService().getAllCategories();
        modelAndView.addObject("allCategories", categories);
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("/categoryManager");

        return modelAndView;
    }

    @RequestMapping(value = "/management/category/update/{id}", method = RequestMethod.GET)
    public ModelAndView gotToUpdateCategoryPage(@PathVariable Integer id)
    {
        ModelAndView modelAndView = new ModelAndView();

        CategoryVo category = this.getCategoryService().findCategoryById(id);
        modelAndView.addObject("category", category);
        modelAndView.setViewName("/categoryUpdate");

        return modelAndView;
    }

    @RequestMapping(value = "/management/category/update", method = RequestMethod.POST)
    public String updateCategory(Integer id, String newName)
    {
        this.getCategoryService().updateCategoryNameById(id, newName);
        String target = "/management/category/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/management/category/create", method = RequestMethod.GET)
    public String goToCreateCategoryPage(String name)
    {
        return "/categoryCreate";
    }

    @RequestMapping(value = "/management/category/create", method = RequestMethod.POST)
    public String createCategory(String name)
    {
        this.logger.info("Start to create category " + name);
        this.getCategoryService().createCategoryByName(name);

        String target = "/management/category/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/management/category/delete/{id}", method = RequestMethod.GET)
    public void deleteCategory(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id)
    {
        String target = "/management/category/listAll.html";
        try
        {
            this.getCategoryService().deleteCategoryById(id);
        }
        catch (HasBlogRelatedException ex)
        {
            this.logger.error(ex.getMessage());
            target += "?errorMsg='" + ex.getMessage() + "'";
        }
        this.logger.info("Redirect to " + target);
        try
        {
            response.sendRedirect(request.getContextPath() + target);
        }
        catch (IOException ex)
        {
            this.logger.error(ex.getMessage());
        }
    }
}
