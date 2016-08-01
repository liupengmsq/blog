package pengliu.me.exception;

/**
 * Created by peng on 16-4-15.
 */
public class UserNotExistException extends Exception
{
    public UserNotExistException(String errorMsg)
    {
        super(errorMsg);
    }
}
