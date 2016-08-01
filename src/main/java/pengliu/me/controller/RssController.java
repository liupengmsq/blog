package pengliu.me.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pengliu.me.vo.BlogVo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RssController extends BaseController
{
	@RequestMapping(value="/rssfeed", method = RequestMethod.GET)
	public ModelAndView getFeedInRss(HttpServletRequest request)
	{
		ModelAndView modelAndView = new ModelAndView();
		List<BlogVo> blogVosForRss = new ArrayList<BlogVo>();
		List<BlogVo> blogVos = this.getBlogService().getAllBlogs();
		for(BlogVo blogVo: blogVos)
		{
			blogVo.setUrl(this.getFullURIPath(request) + "/blog/show/" + blogVo.getId() + ".html");
			blogVosForRss.add(blogVo);
		}

		modelAndView.addObject("feedContent", blogVosForRss);
		modelAndView.setViewName("rssViewer");
		return modelAndView;
	}
}
