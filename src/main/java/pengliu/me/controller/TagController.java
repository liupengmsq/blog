package pengliu.me.controller;

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
import pengliu.me.domain.Tag;
import pengliu.me.exception.HasBlogRelatedException;
import pengliu.me.service.TagService;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.TagVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class TagController extends BaseController
{
    private Logger logger = Logger.getLogger(TagController.class);

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public ModelAndView getBlogsByCategory(@PathVariable Integer tagId, @RequestParam(value = "pageNo", required = false) Integer pageNo)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(pageNo == null)
        {
            pageNo = 1;
        }

        TagVo tagVo = this.getTagService().findTagById(tagId);
        modelAndView.addObject("tag", tagVo);

        Page<BlogVo> publishedBlogs = this.getTagService().getAllPagedPublishedBlogsByTagId(tagId, pageNo, CommonConstant.PAGE_SIZE);
        modelAndView.addObject("pageResult", publishedBlogs);
        addAllTagAndCategoriesToModelAndView(modelAndView);
        addTopTenBlogToModelAndView(modelAndView);
        addFriendlyLinksToModelAndView(modelAndView);
        addBookRecommendationsToModelAndView(modelAndView);
        addPostRecommendationsToModelAndView(modelAndView);
        modelAndView.setViewName("/main");

        return modelAndView;
    }

    @RequestMapping(value = "/management/tag/listAll", method = RequestMethod.GET)
    public ModelAndView listAllTags(@RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<TagVo> categories = this.getTagService().getAllTags();
        modelAndView.addObject("allTags", categories);
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("/tagManager");

        return modelAndView;
    }

    @RequestMapping(value = "/management/tag/update/{id}", method = RequestMethod.GET)
    public ModelAndView gotToUpdateTagPage(@PathVariable Integer id)
    {
        ModelAndView modelAndView = new ModelAndView();

        TagVo tag = this.getTagService().findTagById(id);
        modelAndView.addObject("tag", tag);
        modelAndView.setViewName("/tagUpdate");

        return modelAndView;
    }

    @RequestMapping(value = "/management/tag/update", method = RequestMethod.POST)
    public String updateTag(Integer id, String newName)
    {
        ModelAndView modelAndView = new ModelAndView();
        this.getTagService().updateTagNameById(id, newName);
        String target = "/management/tag/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/management/tag/create", method = RequestMethod.GET)
    public String goToCreateTagPage(String name)
    {
        return "tagCreate";
    }

    @RequestMapping(value = "/management/tag/create", method = RequestMethod.POST)
    public String createTag(String name)
    {
        this.logger.info("Start to create tag " + name);
        this.getTagService().createTagByName(name);

        String target = "/management/tag/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/management/tag/delete/{id}", method = RequestMethod.GET)
    public void deleteTag(HttpServletRequest request, HttpServletResponse response,  @PathVariable Integer id)
    {
        String target = "/management/tag/listAll.html";
        try
        {
            this.getTagService().deleteTagById(id);
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
