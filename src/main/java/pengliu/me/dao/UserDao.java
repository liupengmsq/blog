package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.User;

/**
 * Created by peng on 16-4-13.
 */
@Repository
public class UserDao extends BaseDaoHibernate4<User>
{
    public User findUserByName(String name)
    {
//        return this.get("name", name);
        return this.findUniqueBy("name", name);
    }
}
