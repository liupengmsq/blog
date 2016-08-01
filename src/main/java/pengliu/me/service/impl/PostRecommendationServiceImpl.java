package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.PostRecommendationDao;
import pengliu.me.domain.PostRecommendation;
import pengliu.me.service.PostRecommendationService;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Service
public class PostRecommendationServiceImpl implements PostRecommendationService
{
    private Logger logger = Logger.getLogger(PostRecommendationServiceImpl.class);

    @Autowired
    private PostRecommendationDao postRecommendationDao;

    public List<PostRecommendation> getAllPostRecommendations()
    {
        return this.postRecommendationDao.getAllPostRecommentdations();
    }

    public void deletePostRecommendation(Integer id)
    {
        this.postRecommendationDao.deletePostRecommendationById(id);
    }

    public void createPostRecommendation(String url, String displayName)
    {
        PostRecommendation postRecommendation = new PostRecommendation();
        postRecommendation.setUrl(url);
        postRecommendation.setDisplayName(displayName);

        this.postRecommendationDao.persist(postRecommendation);
    }
}
