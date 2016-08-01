package pengliu.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.common.CommonConstant;
import pengliu.me.domain.PostRecommendation;
import pengliu.me.service.*;
import pengliu.me.vo.CategoryVo;
import pengliu.me.vo.TagVo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ServletContext context;

    @Autowired
    private FriendlyLinkService friendlyLinkService;

    @Autowired
    private PostRecommendationService postRecommendationService;

    @Autowired
    private BookRecommendationService bookRecommendationService;

    protected void addAllTagAndCategoriesToModelAndView(ModelAndView modelAndView)
    {
        List<TagVo> tags = this.tagService.getAllTags();
        modelAndView.addObject("allTags", tags);

        List<CategoryVo> categories = this.getCategoryService().getAllCategories();
        modelAndView.addObject("allCategories", categories);
    }

    protected void addTopTenBlogToModelAndView(ModelAndView modelAndView)
    {
        modelAndView.addObject("topTenBlogs", this.getBlogService().getTopTenLatestPublicBLog());
        modelAndView.addObject("topTenViewCountBlogs", this.getBlogService().getTopTenViewCountPublicBLog());
    }

    protected void addFriendlyLinksToModelAndView(ModelAndView modelAndView)
    {
        modelAndView.addObject("allFriendlyLinks", this.getFriendlyLinkService().getAllFriendlyLinks());
    }

    protected void addBookRecommendationsToModelAndView(ModelAndView modelAndView)
    {
        modelAndView.addObject("allBookRecommendations", this.getBookRecommendationService().getAllBookRecommendations());
    }

    protected void addPostRecommendationsToModelAndView(ModelAndView modelAndView)
    {
        modelAndView.addObject("allPostRecommendations", this.getPostRecommendationService().getAllPostRecommendations());
    }

    protected FriendlyLinkService getFriendlyLinkService()
    {
        return this.friendlyLinkService;
    }

    protected CategoryService getCategoryService() {
        return categoryService;
    }

    protected TagService getTagService() {
        return tagService;
    }

    public BlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    protected String getUploadImageRealPath()
    {
        return this.context.getRealPath(CommonConstant.UPLOAD_PATH);
    }

    protected String getFullURIPath(HttpServletRequest request)
    {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();  // includes leading forward slash
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }

    public BookRecommendationService getBookRecommendationService() {
        return bookRecommendationService;
    }

    public void setBookRecommendationService(BookRecommendationService bookRecommendationService) {
        this.bookRecommendationService = bookRecommendationService;
    }

    public PostRecommendationService getPostRecommendationService() {
        return postRecommendationService;
    }

    public void setPostRecommendationService(PostRecommendationService postRecommendationService) {
        this.postRecommendationService = postRecommendationService;
    }
}
