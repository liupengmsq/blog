package pengliu.me.exception;

/**
 * Created by peng on 16-6-27.
 */
public class CommentUserHasExistException extends Exception
{
    public CommentUserHasExistException(String errorMsg)
    {
        super(errorMsg);
    }
}
