package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.BookRecommendationDao;
import pengliu.me.domain.BookRecommendation;
import pengliu.me.service.BookRecommendationService;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
@Service
public class BookRecommendationServiceImpl implements BookRecommendationService
{
    private Logger logger = Logger.getLogger(BookRecommendationServiceImpl.class);

    @Autowired
    private BookRecommendationDao bookRecommendationDao;

    public List<BookRecommendation> getAllBookRecommendations()
    {
        return this.bookRecommendationDao.getAllBookRecommendation();
    }

    public void deleteBookRecommendation(Integer id)
    {
        this.bookRecommendationDao.delete(id);
    }

    public void createBookRecommendation(String url, String imgSrc, String title)
    {
        BookRecommendation bookRecommendation = new BookRecommendation();
        bookRecommendation.setUrl(url);
        bookRecommendation.setImgSrc(imgSrc);
        bookRecommendation.setTitle(title);

        this.bookRecommendationDao.persist(bookRecommendation);
    }
}
