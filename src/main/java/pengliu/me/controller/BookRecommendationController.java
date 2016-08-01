package pengliu.me.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.domain.BookRecommendation;
import pengliu.me.service.BookRecommendationService;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Controller
@RequestMapping("/management/bookRecommendation")
public class BookRecommendationController extends BaseController
{
    private Logger logger = Logger.getLogger(BookRecommendationController.class);

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAllBookRecommendations (@RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<BookRecommendation> bookRecommendations = this.getBookRecommendationService().getAllBookRecommendations();
        modelAndView.addObject("allBookRecommendations", bookRecommendations);
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("bookRecommendationManager");

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goToCreateBookRecommendation()
    {
        return "bookRecommendationCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createBookRecommendation(@RequestParam("url") String url, @RequestParam("imgSrc") String imgSrc, @RequestParam("title") String title)
    {
        this.getBookRecommendationService().createBookRecommendation(url, imgSrc, title);

        String target = "/management/bookRecommendation/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/delete/{id}.html", method = RequestMethod.GET)
    public String deleteBookRecommendation(@PathVariable Integer id)
    {
        this.getBookRecommendationService().deleteBookRecommendation(id);

        String target = "/management/bookRecommendation/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }
}
