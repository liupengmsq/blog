package pengliu.me.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.dao.FriendlyLinkDao;
import pengliu.me.domain.FriendlyLink;
import pengliu.me.service.FriendlyLinkService;

import java.util.List;

/**
 * Created by peng on 16-6-30.
 */
@Service
public class FriendlyLinkServiceImpl implements FriendlyLinkService
{
    private Logger logger = Logger.getLogger(FriendlyLinkServiceImpl.class);

    @Autowired
    FriendlyLinkDao friendlyLinkDao;

    public List<FriendlyLink> getAllFriendlyLinks()
    {
        return this.friendlyLinkDao.getAllFriendlyLink();
    }

    public void deleteFriendlyLinkById(Integer id)
    {
        this.friendlyLinkDao.deleteFriendlyLinkById(id);
    }

    public void createFriendlyLinke(String url, String linkName)
    {
        FriendlyLink fl = new FriendlyLink();
        fl.setUrl(url);
        fl.setDisplayName(linkName);

        this.friendlyLinkDao.persist(fl);
    }
}
