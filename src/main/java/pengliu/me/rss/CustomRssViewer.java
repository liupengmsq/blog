package pengliu.me.rss;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import pengliu.me.vo.BlogVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomRssViewer extends AbstractRssFeedView
{
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
			HttpServletRequest request)
	{
		feed.setTitle("五环外的老北京");
		feed.setDescription("五环外的老北京");
		feed.setLink("http://192.168.199.248:8081/myblog/");
		
		super.buildFeedMetadata(model, feed, request);
	}

	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		@SuppressWarnings("unchecked")
		List<BlogVo> listContent = (List<BlogVo>) model.get("feedContent");
		List<Item> items = new ArrayList<Item>(listContent.size());
		
		for(BlogVo tempContent : listContent ){
		
			Item item = new Item();
			
			Content content = new Content();
			content.setValue(tempContent.getContent());
			item.setContent(content);
			
			item.setTitle(tempContent.getTitle());
			item.setLink(tempContent.getUrl());
			item.setPubDate(tempContent.getCreateTime());
			
			items.add(item);
		}
		return items;
	}
}