package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.FriendlyLink;

import java.util.List;

/**
 * Created by peng on 16-6-30.
 */
@Repository
public class FriendlyLinkDao extends BaseDaoHibernate4<FriendlyLink>
{
    public List<FriendlyLink> getAllFriendlyLink()
    {
        return this.findAll();
    }

    public void deleteFriendlyLinkById(Integer id)
    {
        this.delete(id);
    }
}
