package pengliu.me.common;

/**
 * Created by peng on 16-6-28.
 */
public enum ErrorMsgCodeEnum
{
    COMMENT_NICKNAME_NOT_EXIST("0", ""),
    COMMENT_EMAIL_NOT_EXIST("1", "");

    private String code;
    private String msg;

    ErrorMsgCodeEnum(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
