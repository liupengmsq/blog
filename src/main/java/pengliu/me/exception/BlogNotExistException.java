package pengliu.me.exception;

/**
 * Created by peng on 4/16/16.
 */
public class BlogNotExistException extends Exception
{
    public BlogNotExistException(String errorMsg)
    {
        super(errorMsg);
    }
}
