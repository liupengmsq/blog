package pengliu.me.service;

import pengliu.me.domain.BookRecommendation;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
public interface BookRecommendationService
{
    List<BookRecommendation> getAllBookRecommendations();
    void deleteBookRecommendation(Integer id);
    void createBookRecommendation(String url, String imgSrc, String title);
}
