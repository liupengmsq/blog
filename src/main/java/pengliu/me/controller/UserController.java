package pengliu.me.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.common.CommonConstant;
import pengliu.me.domain.User;
import pengliu.me.exception.UserNotExistException;
import pengliu.me.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class UserController
{
    private Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout)
    {
        ModelAndView model = new ModelAndView();
        if (error != null)
        {
            model.addObject("error", "Invalid username and password!");
        }
        if (logout != null)
        {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }
}
