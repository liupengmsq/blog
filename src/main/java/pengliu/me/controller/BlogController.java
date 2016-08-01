package pengliu.me.controller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.common.CommonConstant;
import pengliu.me.dao.Page;
import pengliu.me.domain.Category;
import pengliu.me.domain.Comment;
import pengliu.me.domain.CommentUser;
import pengliu.me.domain.Tag;
import pengliu.me.exception.BlogNotExistException;
import pengliu.me.exception.UserNotExistException;
import pengliu.me.service.*;
import pengliu.me.utils.CommonUtil;
import pengliu.me.utils.markdown.MarkdownProcessor;
import pengliu.me.vo.BlogVo;
import pengliu.me.vo.CategoryVo;
import pengliu.me.vo.CommentVo;
import pengliu.me.vo.TagVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController
{
    private Logger logger = Logger.getLogger(BlogController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMainPage(@RequestParam(value = "pageNo", required = false) Integer pageNo)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(pageNo == null)
        {
            pageNo = 1;
        }
        Page<BlogVo> publishedBlogs = this.getBlogService().getAllPagedPublishedBlogs(pageNo, CommonConstant.PAGE_SIZE);
        modelAndView.addObject("pageResult", publishedBlogs);

        addAllTagAndCategoriesToModelAndView(modelAndView);
        addTopTenBlogToModelAndView(modelAndView);
        addFriendlyLinksToModelAndView(modelAndView);
        addBookRecommendationsToModelAndView(modelAndView);
        addPostRecommendationsToModelAndView(modelAndView);
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/search")
    public ModelAndView searchPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "search", required = true) String searchContent)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(pageNo == null)
        {
            pageNo = 1;
        }
        Page<BlogVo> publishedBlogs = this.getBlogService().getAllPagedPublishedBlogsByContent(searchContent, pageNo, CommonConstant.PAGE_SIZE);
        modelAndView.addObject("pageResult", publishedBlogs);
        modelAndView.addObject("searchContent", searchContent);

        addAllTagAndCategoriesToModelAndView(modelAndView);
        addTopTenBlogToModelAndView(modelAndView);
        addFriendlyLinksToModelAndView(modelAndView);
        addBookRecommendationsToModelAndView(modelAndView);
        addPostRecommendationsToModelAndView(modelAndView);
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView goToCreateBlogPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        addAllTagAndCategoriesToModelAndView(modelAndView);
        modelAndView.setViewName("blogCreate");

        List<String> fileNamesSortedByModifiedTime = this.fileService.getAllImageNamesFromServer(this.getUploadImageRealPath());
        modelAndView.addObject("fileNames", fileNamesSortedByModifiedTime);

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createBlog(
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("content") String content,
            @RequestParam("categoryVo") Integer categoryId,
            @RequestParam("tagVos") Integer[] tagIds,
            @RequestParam("status") String status,
            @RequestParam("blogFormat") String format)
    {
        return this.saveOrUpdateBlog(null, title, summary, content, categoryId, tagIds, status, false, format.equals("HTML"));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBlog(@PathVariable Integer id)
    {
        this.logger.info("Start to delete blogVo for id: " + id);
        this.getBlogService().deleteBlogById(id);
        return "redirect:/blog/listAll.html";
    }

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    public ModelAndView listAllBlogs()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allBlogs", this.getBlogService().getAllBlogs());
        modelAndView.setViewName("blogManager");

        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView goToUpdateBlogPage(@PathVariable Integer id)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryVo> categoryVos = this.getCategoryService().getAllCategories();
        modelAndView.addObject("allCategories", categoryVos);

        List<TagVo> tagVos = this.getTagService().getAllTags();
        modelAndView.addObject("allTags", tagVos);

        BlogVo blogVo = null;
        try
        {
            blogVo = this.getBlogService().getBlogById(id);
        }
        catch (BlogNotExistException ex)
        {
            modelAndView.addObject("errorMsg", ex.getMessage());
            modelAndView.addObject("allBlogs", this.getBlogService().getAllBlogs());
            modelAndView.setViewName("blogManager");
            return modelAndView;
        }
        modelAndView.addObject("blog", blogVo);

        CategoryVo checkedCategoryVo = blogVo.getCategoryVo();
        for(CategoryVo categoryVo: categoryVos)
        {
            if(categoryVo.equals(checkedCategoryVo))
            {
                categoryVo.setChecked(true);
            }
        }

        Set<TagVo> checkedTagVos = blogVo.getTagVos();
        for(TagVo tagVo: tagVos)
        {
            if(checkedTagVos.contains(tagVo))
            {
                tagVo.setChecked(true);
            }
        }

        List<CommentVo> commentVos = new ArrayList<CommentVo>();
        try
        {
            commentVos = this.getBlogService().getCommentsByBlogId(id);
        }
        catch (BlogNotExistException ex)
        {
            this.logger.error(ex.getMessage() + "--" + ex.getCause());
        }
        modelAndView.addObject("comments", CommonUtil.sortCommentVosByCreateTimeDesc(commentVos));
        modelAndView.addObject("commentsCount", commentVos.size());

        List<String> fileNamesSortedByModifiedTime = this.fileService.getAllImageNamesFromServer(this.getUploadImageRealPath());
        modelAndView.addObject("fileNames", fileNamesSortedByModifiedTime);

        modelAndView.setViewName("blogUpdate");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateBlog(
            @RequestParam("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("content") String content,
            @RequestParam("categoryVo") Integer categoryId,
            @RequestParam("tagVos") Integer[] tagIds,
            @RequestParam("status") String status,
            @RequestParam("blogFormat") String format)
    {
        return this.saveOrUpdateBlog(id, title, summary, content, categoryId, tagIds, status, true, format.equals("HTML"));
    }

    private ModelAndView saveOrUpdateBlog(
            Integer id,
            String title,
            String summary,
            String content,
            Integer categoryId,
            Integer[] tagIds,
            String status,
            boolean isUpdate,
            boolean isHTML)
    {
        ModelAndView modelAndView = new ModelAndView();

        this.logger.info("Get category by category id");
        Category category = this.getCategoryService().findCategoryPoById(categoryId);

        this.logger.info("Get tags by tag ids");
        List<Tag> tags = this.getTagService().findTagsPoByIds(tagIds);

        BlogVo blogVo = new BlogVo();
        if(isUpdate)
        {
            try
            {
                blogVo = this.getBlogService().getBlogById(id);
            }
            catch (BlogNotExistException ex)
            {
                modelAndView.setViewName("blogUpdate");
                modelAndView.addObject("errorMsg", ex.getMessage());
                return modelAndView;
            }
        }

        blogVo.setTitle(title);
        blogVo.setSummary(summary);
        if(isHTML)
        {
            blogVo.setContent(content);
            blogVo.setFormat("HTML");
        }
        else
        {
            blogVo.setContent(new MarkdownProcessor().markdown(content));
            blogVo.setFormat("MarkDown");
        }
        blogVo.setStatus(status);


        try
        {
            if(isUpdate)
            {
                this.logger.info("Update blogVo");
                this.getBlogService().updateBlog(blogVo, category, tags);
            }
            else
            {
                this.logger.info("Create blogVo");
                this.getBlogService().createBlog(blogVo, category, tags);
            }
        }
        catch (UserNotExistException ex)
        {
            this.logger.error("Admin user doesn't exist!!!");
            modelAndView.addObject("errorMsg", ex.getMessage());
            addAllTagAndCategoriesToModelAndView(modelAndView);

            if(isUpdate)
            {
                modelAndView.setViewName("blogUpdate");
            }
            else
            {
                modelAndView.setViewName("blogCreate");
            }
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/blog.html");
        return modelAndView;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView showBlog(HttpServletRequest request, @PathVariable Integer id, @RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = this.goToUpdateBlogPage(id);
//        BlogVo blogVo = (BlogVo) modelAndView.getModel().get("blog");
//        if(blogVo != null)
//        {
//            blogVo.setContent(new MarkdownProcessor().markdown(blogVo.getContent()));
//        }
        this.addTopTenBlogToModelAndView(modelAndView);
        this.addFriendlyLinksToModelAndView(modelAndView);
        this.addBookRecommendationsToModelAndView(modelAndView);
        this.addPostRecommendationsToModelAndView(modelAndView);

        HttpSession session = request.getSession();
        CommentUser commentUser = (CommentUser) session.getAttribute(CommonConstant.COMMENT_USER_CONTEXT);
        CommentVo commentVo = new CommentVo();
        if(commentUser != null)
        {
            commentVo.setUserName(commentUser.getName());
            commentVo.setUserEmail(commentUser.getEmail());
            commentVo.setUserUrl(commentUser.getBlogUrl());
        }

        modelAndView.addObject("commentForm", commentVo);
        if(!StringUtils.isEmpty(errorMsg))
        {
            try
            {
                errorMsg = URLDecoder.decode(errorMsg, "UTF-8");
            }
            catch (UnsupportedEncodingException ex)
            {
                this.logger.error(ex.getMessage());
            }
        }
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("blogDisplay");
        this.getBlogService().plusBlogViewCount(id);
        return modelAndView;
    }

    @RequestMapping(value = "/uploadImage")
    public ModelAndView updateThumb(HttpServletRequest request,
                                    @RequestParam("name") String name,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "blogId", required = false) Integer blogId)
    {
        ModelAndView mav = null;
        if(blogId == null)
        {
            mav = goToCreateBlogPage();
        }
        else
        {
            mav = goToUpdateBlogPage(blogId);
        }
        if(!file.isEmpty())
        {
            //获取upload文件夹得真实路径
            String realpath = getUploadImageRealPath();
            this.logger.info("Get saving path for image: " + realpath);
            File f;
            if(StringUtils.isEmpty(name))
            {
                f = new File(realpath + "/" + file.getOriginalFilename());
            }
            else
            {
                f = new File(realpath + "/" + name);
            }

            if(f.exists())
            {
                try
                {
                    mav.addObject("errorMsg", String.format("The file '%s' has been exist!!", f.getCanonicalPath()));
                }
                catch (IOException ex)
                {
                    mav.addObject("errorMsg", ex.getMessage());
                }
                return mav;
            }
            else
            {
                try
                {
                    //Apache的上传文件的工具类
                    FileUtils.copyInputStreamToFile(file.getInputStream(), f);
                    if(blogId == null)
                    {
                        //再次调用goToCreateBlogPage方法,以便重新获取刚刚上传的文件
                        mav = goToCreateBlogPage();
                    }
                    else
                    {
                        mav = goToUpdateBlogPage(blogId);
                    }
                }
                catch (IOException ex)
                {
                    mav.addObject("errorMsg", ex.getMessage());
                }
            }
        }
        return mav;
    }
}
