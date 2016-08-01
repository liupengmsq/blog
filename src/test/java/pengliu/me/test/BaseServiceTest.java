package pengliu.me.test;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Created by peng on 16-4-11.
 */
@SpringApplicationContext({"applicationContext.xml"})
public class BaseServiceTest extends UnitilsJUnit4
{
    @SpringApplicationContext
    public ApplicationContext applicationContext;
}
