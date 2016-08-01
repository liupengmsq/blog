package pengliu.me.service;

import pengliu.me.domain.PostRecommendation;

import java.util.List;

/**
 * Created by peng on 16-7-5.
 */
public interface PostRecommendationService
{
    List<PostRecommendation> getAllPostRecommendations();
    void deletePostRecommendation(Integer id);
    void createPostRecommendation(String url, String displayName);
}
