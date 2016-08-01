package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.PostRecommendation;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Repository
public class PostRecommendationDao extends BaseDaoHibernate4<PostRecommendation>
{
    public List<PostRecommendation> getAllPostRecommentdations()
    {
        return this.findAll();
    }

    public void deletePostRecommendationById(Integer id)
    {
        this.delete(id);
    }
}
