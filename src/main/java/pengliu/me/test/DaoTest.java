package pengliu.me.test;


import org.hibernate.Session;
import org.hibernate.Transaction;
import pengliu.me.common.UserStatus;
import pengliu.me.domain.User;
import pengliu.me.utils.CommonUtil;


/**
 * Created by peng on 4/9/16.
 */
public class DaoTest
{
    public static void main(String[] args) throws Exception
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();

            User user = new User();
//            user.setLastLoginTime(CommonUtil.getTimeStampNow());
            user.setName("liupeng");
            user.setPassword("123");
//            user.setStatus(UserStatus.ACTIVE);
            session.save(user);

            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
                tx.rollback();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    private static void enumTest()
    {
        UserStatus status = UserStatus.ACTIVE;
        System.out.println(status);
        System.out.println(status.toString());
        System.out.println(status.ordinal());
        System.out.println(status.name());
    }
}
