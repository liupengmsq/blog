package pengliu.me.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.domain.PostRecommendation;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Controller
@RequestMapping("/management/postRecommendation")
public class PostRecommendationController extends BaseController
{
    private Logger logger = Logger.getLogger(PostRecommendationController.class);

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAllPostRecommendations (@RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<PostRecommendation> postRecommendations = this.getPostRecommendationService().getAllPostRecommendations();
        modelAndView.addObject("allPostRecommendations", postRecommendations);
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("postRecommendationManager");

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goToCreatePostRecommendation()
    {
        return "postRecommendationCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPostRecommendation(@RequestParam("url") String url, @RequestParam("displayName") String displayName)
    {
        this.getPostRecommendationService().createPostRecommendation(url, displayName);

        String target = "/management/postRecommendation/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/delete/{id}.html", method = RequestMethod.GET)
    public String deletePostRecommendation(@PathVariable Integer id)
    {
        this.getPostRecommendationService().deletePostRecommendation(id);

        String target = "/management/postRecommendation/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }
}
