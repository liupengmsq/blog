package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.BookRecommendation;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Repository
public class BookRecommendationDao extends BaseDaoHibernate4<BookRecommendation>
{
    public List<BookRecommendation> getAllBookRecommendation()
    {
        return this.findAll();
    }

    public void deleteBookRecommendation(Integer id)
    {
        this.delete(id);
    }
}
