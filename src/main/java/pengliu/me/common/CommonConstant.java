package pengliu.me.common;

/**
 * Created by peng on 16-4-14.
 */
public class CommonConstant
{
    /**
     * 用户对象放到Session中的键名称
     */
    public static final String USER_CONTEXT = "USER_CONTEXT";

    /**
     * 用户登陆后需要跳转回去的页面URL， 放到Session中的键名称
     */
    public static final String LOGIN_TO_URL = "LOGIN_TO_URL";

    /**
     * 用户登陆后默认的跳转页面URL
     */
    public static final String DEFAULT_URL = "/index.jsp";

    /**
     * 每页的记录数
     */
    public static final int PAGE_SIZE = 20;

    public static final String ADMIN_USER_NAME = "peng";

    public static final String UPLOAD_PATH = "/resources";

    /**
     * 评论用户对象放到Session中的键名称
     */
    public static final String COMMENT_USER_CONTEXT = "COMMENT_USER_CONTEXT";
}
