package pengliu.me.test;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import pengliu.me.dao.BaseDao;

/**
 * Created by peng on 4/10/16.
 */
@SpringApplicationContext( {"applicationContext.xml" })
public class BaseDaoTest extends UnitilsJUnit4
{
    private Logger logger = Logger.getLogger(BaseDaoTest.class);

    @Before
    public void init()
    {
        this.logger.info("In init method");
    }

    @After
    public void cleanup()
    {
        this.logger.info("In clean method");
    }
}
