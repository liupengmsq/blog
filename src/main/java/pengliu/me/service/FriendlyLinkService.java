package pengliu.me.service;

import pengliu.me.domain.FriendlyLink;

import java.util.List;

/**
 * Created by peng on 16-6-30.
 */
public interface FriendlyLinkService
{
    List<FriendlyLink> getAllFriendlyLinks();
    void deleteFriendlyLinkById(Integer id);
    void createFriendlyLinke(String url, String linkName);
}
