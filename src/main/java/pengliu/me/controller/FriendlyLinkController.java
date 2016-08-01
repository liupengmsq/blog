package pengliu.me.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.domain.FriendlyLink;
import pengliu.me.service.FriendlyLinkService;

import java.util.List;

/**
 * Created by peng on 16-6-30.
 */
@Controller
@RequestMapping("/management/friendlyLink")
public class FriendlyLinkController extends BaseController
{
    private Logger logger = Logger.getLogger(FriendlyLinkController.class);

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAllFriendlyLinks(@RequestParam(value = "errorMsg", required = false) String errorMsg)
    {
        ModelAndView modelAndView = new ModelAndView();

        List<FriendlyLink> friendlyLinks = this.getFriendlyLinkService().getAllFriendlyLinks();
        modelAndView.addObject("allFriendlyLinks", friendlyLinks);
        modelAndView.addObject("errorMsg", errorMsg);
        modelAndView.setViewName("friendlyLinkManager");

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goToCreateFriendlyLink()
    {
        return "friendlyLinkCreate";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createFriendlyLink(@RequestParam("url") String url, @RequestParam("linkName") String linkName)
    {
        this.getFriendlyLinkService().createFriendlyLinke(url, linkName);

        String target = "/management/friendlyLink/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }

    @RequestMapping(value = "/delete/{id}.html", method = RequestMethod.GET)
    public String deleteFriendlyLink(@PathVariable Integer id)
    {
        this.getFriendlyLinkService().deleteFriendlyLinkById(id);

        String target = "/management/friendlyLink/listAll.html";
        this.logger.info("Redirect to " + target);

        return "redirect:" + target;
    }
}
