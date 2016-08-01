package pengliu.me.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by peng on 16-4-11.
 */
@RunWith(Parameterized.class)
public class JUnite4ParameterTest
{
    private SimpleDateFormat simpleDateFormat;
    private String date;
    private String dateformat;
    private String expectedDate;

    public JUnite4ParameterTest(String date, String dateformat, String expectedDate)
    {
        this.date = date;
        this.dateformat = dateformat;
        this.expectedDate = expectedDate;
    }

    // 参数化的输出方法，Runner将产生三个JUnite4ParameterTest实例，
    // 其字段date, dateformat与expected分别被赋予此方法返回的Collection中的每条输出
    @Parameterized.Parameters
    public static Collection getParameters()
    {
        List ret = new ArrayList();
        ret.add(new String[]{"2012-04-01 12:23:12", "yyyyMMdd", "20120401"});
        ret.add(new String[]{"2012-04-01 12:23:12", "yyyy-MM-dd", "2012-04-01"});
        ret.add(new String[]{"2012-04-01 12:23:12", "yyyy, MM, dd", "2012, 04, 01"});
        return ret;
    }

    @Test
    public void testSimpleDateFormat() throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = simpleDateFormat.parse(this.date);
        this.simpleDateFormat = new SimpleDateFormat(this.dateformat);
        String result = this.simpleDateFormat.format(d);
        Assert.assertEquals(this.expectedDate, result);
    }
}
