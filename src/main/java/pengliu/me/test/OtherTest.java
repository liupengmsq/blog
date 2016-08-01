package pengliu.me.test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 16-4-21.
 */
public class OtherTest
{
    public static void main(String[] args)
    {
        List<Integer> testList = new ArrayList<Integer>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);

        List<Integer> subList = testList.subList(0, 5);

    }
}
